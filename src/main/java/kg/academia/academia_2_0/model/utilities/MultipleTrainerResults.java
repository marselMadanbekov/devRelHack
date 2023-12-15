package kg.academia.academia_2_0.model.utilities;

import lombok.Data;

@Data
public class MultipleTrainerResults {
    private Long pupilId1;
    private String topic1;
    private Boolean isCorrect1;

    private Long pupilId2;
    private String topic2;
    private Boolean isCorrect2;

    private Long pupilId3;
    private String topic3;
    private Boolean isCorrect3;

    private Long pupilId4;
    private String topic4;
    private Boolean isCorrect4;
}
