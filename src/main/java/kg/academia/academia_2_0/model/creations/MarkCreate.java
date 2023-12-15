package kg.academia.academia_2_0.model.creations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarkCreate {
    private Integer totalPoints;
    private Integer earnedPoints;
    private Long subjectId;
    private Integer markType;
    private Long pupilId;
    private String topic;
}
