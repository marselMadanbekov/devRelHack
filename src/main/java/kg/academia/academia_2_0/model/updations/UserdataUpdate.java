package kg.academia.academia_2_0.model.updations;

import lombok.Data;

@Data
public class UserdataUpdate {
    private Long employeeId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Integer level;
    private String dateOfBirth;
    private String password;
    private String confirmPassword;
}
