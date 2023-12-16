package kg.academia.academia_2_0.model.creations;

import kg.academia.academia_2_0.model.enums.Gender;
import kg.academia.academia_2_0.model.enums.Level;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Data
public class EmployeeCreate {
    private String firstname;
    private String lastname;
    private Integer gender;
    private String phoneNumber;
    private Date dateOfBirth;
    private String username;
    private String password;
    private String position;
    private Double rating;
    private Level level;
    private List<String> skills;
    private Map<String,String> socialMedias;
    private Integer role;
}
