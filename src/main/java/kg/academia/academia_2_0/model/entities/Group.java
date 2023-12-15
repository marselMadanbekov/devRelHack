package kg.academia.academia_2_0.model.entities;


import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.model.enums.DayOfWeek;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"group\"")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private Subject subject;
    @ManyToOne()
    private Teacher teacher;
    @ManyToMany
    private List<Pupil> pupils;
    @ElementCollection
    private Map<DayOfWeek,String> timetable;
    private Boolean active;
    private Date createDate;
    @PrePersist()
    public void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
    }

    public void addPupil(Pupil pupil){
        if(pupils.contains(pupil))
            throw new RuntimeException("Ученик уже состоит в этой группе");
        pupils.add(pupil);
    }

    public void removePupil(Pupil pupil){
        pupils.remove(pupil);
    }

    public void addLessonToTimetable(DayOfWeek dayOfWeek, String clock){
        timetable.put(dayOfWeek,clock);
    }

    public void deleteDayFromTimetable(DayOfWeek value) {
        timetable.remove(value);
    }
}
