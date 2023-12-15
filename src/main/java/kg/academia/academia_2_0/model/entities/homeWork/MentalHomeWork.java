package kg.academia.academia_2_0.model.entities.homeWork;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MentalHomeWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date createDate;
    @ManyToOne
    private Group group;
    private Date deadLine;
    @OneToMany
    private List<MentalExercise> mentalExercises = new ArrayList<>();
    @ManyToOne
    private Pupil pupil;
    @PrePersist()
    public void onCreate(){
        this.createDate = Date.valueOf(LocalDate.now());
    }


    public void addExercise(MentalExercise mentalExercise) {
        this.mentalExercises.add(mentalExercise);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MentalHomeWork homeWork = (MentalHomeWork) o;
        return Objects.equals(id, homeWork.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int totalQuestionsInAllExercises(){
        return mentalExercises.stream()
                .mapToInt(MentalExercise::getQuestionCount)
                .sum();
    }

    public int totalSolvedQuestionsInAllExercises(){
        return mentalExercises.stream()
                .mapToInt(MentalExercise::getSolved)
                .sum();
    }

    public boolean isExpire(){
        return LocalDate.now().isAfter(this.deadLine.toLocalDate());
    }
}
