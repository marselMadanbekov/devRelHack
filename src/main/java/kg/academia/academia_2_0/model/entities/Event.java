package kg.academia.academia_2_0.model.entities;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
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
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @ElementCollection
    private List<String> targetSkills;
    @ManyToOne
    private Employee organizer;
    @ElementCollection
    private Map<Employee, Boolean> participantsAttendance;

    public void upgradeAttendance(Employee current, boolean isPresent) {
        this.participantsAttendance.put(current, isPresent);
    }
}
