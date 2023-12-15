package kg.academia.academia_2_0.model.entities;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @ManyToOne
    private Teacher teacher;
    @ElementCollection
    private Map<Pupil, Boolean> attendance;
    @ManyToOne
    private Group group;

    public void upgradeAttendance(Pupil current, boolean isPresent) {
        this.attendance.put(current, isPresent);
    }
}
