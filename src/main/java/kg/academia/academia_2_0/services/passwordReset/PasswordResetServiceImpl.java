//package kg.academia.academia_2_0.services.passwordReset;
//
//
//import kg.academia.academia_2_0.model.entities.ConfirmationCode;
//import kg.academia.academia_2_0.model.entities.users.Employee;
//import kg.academia.academia_2_0.repositories.ConfirmationCodeRepository;
//import kg.academia.academia_2_0.services.email.EmailServiceImpl;
//import kg.academia.academia_2_0.services.user.UserStorage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Random;
//
//@Service
//public class PasswordResetServiceImpl implements PasswordResetService {
//    private final UserStorage userStorage;
//    private final ConfirmationCodeRepository confirmationCodeRepository;
//    private final EmailServiceImpl emailService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public PasswordResetServiceImpl(UserStorage userStorage, ConfirmationCodeRepository confirmationCodeRepository, EmailServiceImpl emailService, PasswordEncoder passwordEncoder) {
//        this.userStorage = userStorage;
//        this.confirmationCodeRepository = confirmationCodeRepository;
//        this.emailService = emailService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    @Transactional
//    public void passwordResetCreateConfirmationCodeByUsername(String username) {
//        Employee employee = userStorage.getUserDataByUsername(username);
//        if (employee.getEmail() != null && !employee.getEmail().isEmpty()) {
//            ConfirmationCode confirmationCode = confirmationCodeRepository.findByUserData(employee).orElse(new ConfirmationCode());
//            if (confirmationCode.isCodeNeededUpdating()) {
//                confirmationCode.setCode(generateNewConfirmationCode());
//                confirmationCode.setEmployee(employee);
//                confirmationCodeRepository.save(confirmationCode);
//            }
//            emailService.sendPasswordResetMail(employee.getEmail(),confirmationCode.getCode());
//        }else
//            throw new RuntimeException("У пользователя не указан адрес электронной почты для отправки кода подтверждения, обратитесь к администратору вашего филиала для изменения пароля.");
//    }
//
//    @Override
//    public void checkConfirmationCode(String username, String confirmationCode) {
//        Employee employee = userStorage.getUserDataByUsername(username);
//        ConfirmationCode confirmationCodeEntity = confirmationCodeRepository.findByUserData(employee).orElseThrow(() -> new RuntimeException("Ошибка при подтверждении кода доступа. Код доступа не найден, попробуйте сбросить пароль снова!"));
//        if(!confirmationCode.equals(confirmationCodeEntity.getCode()))
//            throw new RuntimeException("Неправильный код подтверждения!");
//    }
//
//    @Override
//    public void resetPassword(String username, String confirmationCode, String password, String confirmPassword) {
//        checkConfirmationCode(username,confirmationCode);
//        Employee employee = userStorage.getUserDataByUsername(username);
//
//        employee.setPassword(passwordEncoder.encode(password));
//        userStorage.saveUserdata(employee);
//    }
//
//    private String generateNewConfirmationCode() {
//        Random random = new Random();
//        int code = 100000 + random.nextInt(900000);
//        return String.valueOf(code);
//    }
//}
