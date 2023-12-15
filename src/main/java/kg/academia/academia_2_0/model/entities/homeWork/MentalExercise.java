package kg.academia.academia_2_0.model.entities.homeWork;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kg.academia.academia_2_0.model.enums.ExerciseType;
import kg.academia.academia_2_0.model.enums.TrainerTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TrainerTask topic;
    private int questionCount;
    private int digitsCount;
    private int numbersCount;
    private Double speed;
    private ExerciseType type;

    private int solved;
    private int correct;
    private int incorrect;

    public void addSolvedQuestions(int count){
        solved += count;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MentalExercise mentalExercise = (MentalExercise) o;
        return id.equals(mentalExercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addCorrectQuestions(Integer correctAnswers) {
        this.correct += correctAnswers;
    }

    public void clearProgress() {
        this.solved = 0;
        this.correct = 0;
    }
}
