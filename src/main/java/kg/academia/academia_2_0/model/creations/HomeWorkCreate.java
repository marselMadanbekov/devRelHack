package kg.academia.academia_2_0.model.creations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HomeWorkCreate {
    private Long pupilId;
    private Date deadLine;
    private Long groupId;
}
