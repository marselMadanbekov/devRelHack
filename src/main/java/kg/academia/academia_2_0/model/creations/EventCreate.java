package kg.academia.academia_2_0.model.creations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventCreate {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String name;
    private String description;
}
