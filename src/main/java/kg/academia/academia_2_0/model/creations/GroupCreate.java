package kg.academia.academia_2_0.model.creations;

import lombok.Data;

@Data
public class GroupCreate {
    private String name;
    private Long subjectId;
    private Long teacherId;
    private Long branchId;
}
