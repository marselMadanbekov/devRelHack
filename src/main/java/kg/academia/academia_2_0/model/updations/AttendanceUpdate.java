package kg.academia.academia_2_0.model.updations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceUpdate {
    private Long lessonId;
    private Map<Long,Boolean> attendance;
}
