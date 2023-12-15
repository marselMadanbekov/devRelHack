package kg.academia.academia_2_0.model.creations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonCreate {
    private Long groupId;
    private String date;
    private Map<Long, Boolean> attendance;
}
