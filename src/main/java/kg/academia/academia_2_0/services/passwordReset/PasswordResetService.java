package kg.academia.academia_2_0.services.passwordReset;


public interface PasswordResetService {
    void passwordResetCreateConfirmationCodeByUsername(String username);

    void checkConfirmationCode(String username, String confirmationCode);

    void resetPassword(String username, String confirmationCode, String password, String confirmPassword);
}
