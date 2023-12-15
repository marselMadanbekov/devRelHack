package kg.academia.academia_2_0.model.updations;

import lombok.Data;

@Data
public class UserdataUpdate {
    private Long userdataId;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private String dateOfBirth;
    private String password;
    private String confirmPassword;
}
