package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.enums.Gender;
import kg.academia.academia_2_0.model.enums.Role;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.security.AccessGuardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserStorage userStorage;
    private final AccessGuardService accessGuardService;
    private final NotificationService notificationService;
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserStorage userStorage, AccessGuardService accessGuardService, NotificationService notificationService) {
        this.passwordEncoder = passwordEncoder;
        this.userStorage = userStorage;
        this.accessGuardService = accessGuardService;
        this.notificationService = notificationService;
    }

    @Override
    public void createEmployee(EmployeeCreate employeeCreate) {

    }

    @Override
    public void updateUserdata(UserdataUpdate update) {
        Employee employee = userStorage.getEmployeeById(update.getUserdataId());

        if (!update.getFirstname().isEmpty())
            employee.setFirstname(update.getFirstname());

        if (!update.getLastname().isEmpty())
            employee.setLastname(update.getLastname());

        if (!update.getPhoneNumber().isEmpty())
            employee.setPhoneNumber(update.getPhoneNumber());

        if (!update.getDateOfBirth().isEmpty())
            employee.setDateOfBirth(Date.valueOf(update.getDateOfBirth()));

        if (!update.getPassword().isEmpty()) {
            if (!update.getPassword().equals(update.getConfirmPassword()))
                throw new RuntimeException("Пароль и подтверждение не совпадают!");
            employee.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        userStorage.saveEmployee(employee);
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
