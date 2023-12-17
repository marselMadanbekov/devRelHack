package kg.academia.academia_2_0.services.email;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl {

    private final JavaMailSender emailSender;


    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String targetUser, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(targetUser);
        message.setSubject("ManasBM");
        message.setText(text);
        emailSender.send(message);
    }

    public void sendPasswordResetMail(String targetUser, String confirmationCode){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(targetUser);
        message.setSubject("ManasBM");
        message.setText(passwordResetMail(confirmationCode));
        emailSender.send(message);
    }


    private String passwordResetMail(String confirmationCode){
        return "Код подтверждения для сброса пароля ( " + confirmationCode + " ). Не передавайте код никому, код будет действителен в течение 5 мин с начала момента отправки.";
    }

}
