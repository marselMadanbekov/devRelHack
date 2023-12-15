package kg.academia.academia_2_0.model.creations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchCreate {
    private String state;
    private String town;
    private String name;
}
