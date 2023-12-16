package kg.academia.academia_2_0.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm") // Устанавливаем желаемый формат даты и времени
    private LocalDateTime startDateTime;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm") // Устанавливаем желаемый формат даты и времени
    private LocalDateTime endDateTime;
    private String photo;
    @ElementCollection
    private List<String> targetSkills;
    @ManyToOne
    private Employee organizer;
    @ElementCollection
    private Map<Employee, Boolean> participantsAttendance;

    public void upgradeAttendance(Employee current, boolean isPresent) {
        this.participantsAttendance.put(current, isPresent);
    }

    public String getStartDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM HH:mm"); // Желаемый формат
        return startDateTime.format(formatter);
    }
    public String getEndDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMMM HH:mm"); // Желаемый формат
        return endDateTime.format(formatter);
    }

    public void addParticipants(Employee employee) {
        if(participantsAttendance == null){
            participantsAttendance = new HashMap<>();
        }
        participantsAttendance.put(employee, false);
    }
}
