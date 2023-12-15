package kg.academia.academia_2_0.model.creations;

import lombok.Data;

@Data
public class SubjectCreate {
    private String name;
    private Integer costPerLesson;
    private Long branchId;
}
