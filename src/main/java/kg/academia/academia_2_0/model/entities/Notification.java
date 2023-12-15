package kg.academia.academia_2_0.model.entities;


import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.UserData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String message;
    @ManyToOne
    private UserData targetUser;
    private Date createDate;
    private Boolean viewed;
    @PrePersist()
    public void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
    }
}
