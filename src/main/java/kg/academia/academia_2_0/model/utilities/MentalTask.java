package kg.academia.academia_2_0.model.utilities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentalTask {
    private Integer[] taskEntry;
    private Integer answer;
}
