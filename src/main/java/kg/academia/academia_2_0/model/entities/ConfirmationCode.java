package kg.academia.academia_2_0.model.entities;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.UserData;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ConfirmationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @OneToOne
    private UserData userData;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdate;

    @PrePersist
    public void onCreate(){
        this.createDate = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }

    public boolean isCodeNeededUpdating(){
        if(code == null)
            return true;
        if(code.isEmpty())
            return true;
        return LocalDateTime.now().minusMinutes(5L).isAfter(lastUpdate);
    }
}
