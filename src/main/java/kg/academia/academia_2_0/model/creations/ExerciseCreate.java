package kg.academia.academia_2_0.model.creations;

import lombok.Data;

@Data
public class ExerciseCreate {
    private Long homeworkId;
    private String topic;
    private Integer questionsCount;
    private Integer digitsCount;
    private Integer numbersCount;
    private Double speed;
    private Integer type;
}
