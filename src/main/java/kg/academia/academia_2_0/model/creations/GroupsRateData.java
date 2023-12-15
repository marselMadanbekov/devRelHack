package kg.academia.academia_2_0.model.creations;

import lombok.Data;

import java.util.List;

@Data
public class GroupsRateData {
    private Long subjectId;
    private Integer markType;
    private List<PupilsRate> pupilsRate;
}
