package kg.academia.academia_2_0.model.creations;

import lombok.Data;

import java.sql.Date;

@Data
public class UserCreate {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Integer gender;
    private String email;
    private Date dateOfBirth;
    private String username;
    private String password;
    private Integer role;
    private Long branchId;
}
