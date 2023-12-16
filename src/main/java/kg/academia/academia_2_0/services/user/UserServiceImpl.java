package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.enums.Gender;
import kg.academia.academia_2_0.model.enums.Level;
import kg.academia.academia_2_0.model.enums.Role;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import kg.academia.academia_2_0.services.email.EmailServiceImpl;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.security.AccessGuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserStorage userStorage;
    private final EmailServiceImpl emailService;
    private final AccessGuardService accessGuardService;
    private final NotificationService notificationService;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserStorage userStorage, EmailServiceImpl emailService, AccessGuardService accessGuardService, NotificationService notificationService) {
        this.passwordEncoder = passwordEncoder;
        this.userStorage = userStorage;
        this.emailService = emailService;
        this.accessGuardService = accessGuardService;
        this.notificationService = notificationService;
    }

    @Override
    public void createEmployee(EmployeeCreate employeeCreate) {
        Employee employee = userStorage.saveEmployee(Employee.builder()
                .active(true)
                .firstname(employeeCreate.getFirstname())
                .lastname(employeeCreate.getLastname())
                .dateOfBirth(employeeCreate.getDateOfBirth())
                .gender(Gender.values()[employeeCreate.getGender()])
                .level(Level.values()[employeeCreate.getLevel()])
                .role(Role.values()[employeeCreate.getRole()])
                .rating(0D)
                .position(employeeCreate.getPosition())
                .phoneNumber(employeeCreate.getPhoneNumber())
                .username(employeeCreate.getUsername())
                .password(passwordEncoder.encode(employeeCreate.getPassword()))
                .build());
        employee.addSocialMedia("EMAIL", employeeCreate.getEmail());
        userStorage.saveEmployee(employee);
    }

    @Override
    public void updateUserdata(UserdataUpdate update) {
        Employee employee = userStorage.getEmployeeById(update.getEmployeeId());

        if (!update.getFirstname().isEmpty())
            employee.setFirstname(update.getFirstname());

        if (!update.getLastname().isEmpty())
            employee.setLastname(update.getLastname());

        if (!update.getPhoneNumber().isEmpty())
            employee.setPhoneNumber(update.getPhoneNumber());

        if (!update.getDateOfBirth().isEmpty())
            employee.setDateOfBirth(Date.valueOf(update.getDateOfBirth()));

        employee.setLevel(Level.values()[update.getLevel()]);

        if (!update.getPassword().isEmpty()) {
            if (!update.getPassword().equals(update.getConfirmPassword()))
                throw new RuntimeException("Пароль и подтверждение не совпадают!");
            employee.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        userStorage.saveEmployee(employee);
    }

    @Override
    public Page<Employee> getEmployeesByPage(Integer page) {
        return userStorage.getEmployeesByPage(page);
    }

    @Override
    public Employee findEmployeeById(Long userId) {
        return userStorage.getEmployeeById(userId);
    }

    @Override
    public List<String> findUniqueSkills() {
        return userStorage.findUniqueSkills();
    }

    @Override
    public void addSkill(Long employeeId, String skillName) {
        Employee employee = userStorage.getEmployeeById(employeeId);
        employee.addSkill(skillName);
        userStorage.saveEmployee(employee);
    }

    @Override
    public void deleteSkill(Long employeeId, String skillName) {
        Employee employee = userStorage.getEmployeeById(employeeId);
        employee.removeSkill(skillName);
        userStorage.saveEmployee(employee);
    }

    @Override
    public void addSocMediaToEmployee(Long employeeId, String socMedia, String username) {
        Employee employee = userStorage.getEmployeeById(employeeId);
        employee.addSocialMedia(socMedia.toUpperCase(),username);
        userStorage.saveEmployee(employee);
    }
    @Override
    public void deleteSocMedia(Long employeeId, String socMedia) {
        Employee employee = userStorage.getEmployeeById(employeeId);
        employee.removeSocMedia(socMedia);
        userStorage.saveEmployee(employee);
    }

    @Override
    public void sendDistributionsByUser(Long userId, String message, List<String> targetDistributions) {
        Employee employee = userStorage.getEmployeeById(userId);

        for(String distribution : targetDistributions){
            if(distribution.equalsIgnoreCase("EMAIL")){
                sendEmailByUsername(message,employee.getSocialMedias().get("EMAIL"));
            }else{
                sendTelegramDistributionsByUser(employee,message);
            }
        }
    }

    private void sendEmailByUsername(String message, String email) {
        emailService.sendSimpleMessage(email,message);
    }

    public void sendTelegramDistributionsByUser(Employee employee, String message) {
        Map<String, Object> request = new HashMap<>();
        request.put("text", message);
        Map<String, Integer> requestEntry = new HashMap<>();
        requestEntry.put(employee.getSocialMedias().get("TELEGRAM"),1);

        request.put("users", requestEntry);

        try {
            // Создаем RestTemplate
            RestTemplate restTemplate = new RestTemplate();

            // URL для отправки запроса
            String url = "http://192.168.43.33:2323/api/telegram/distribution";

            // Заголовки запроса
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Тело запроса

            // Создаем объект HttpEntity с заголовками и телом запроса
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);

            // Отправляем POST-запрос и получаем ответ
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

            // Получаем код ответа
            HttpStatusCode statusCode = response.getStatusCode();
            System.out.println("Response Code: " + statusCode);

            // Можно получить тело ответа, если это необходимо
            String responseBody = response.getBody();
            System.out.println("Response Body: " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Employee createUserData(EmployeeCreate employeeCreate) {
        return Employee.builder()
                .active(true)
                .firstname(employeeCreate.getFirstname())
                .lastname(employeeCreate.getLastname())
                .username(employeeCreate.getUsername())
                .gender(Gender.values()[employeeCreate.getGender()])
                .password(passwordEncoder.encode(employeeCreate.getPassword()))
                .dateOfBirth(employeeCreate.getDateOfBirth())
                .phoneNumber(employeeCreate.getPhoneNumber())
                .role(Role.values()[employeeCreate.getRole()])
                .build();
    }

}
