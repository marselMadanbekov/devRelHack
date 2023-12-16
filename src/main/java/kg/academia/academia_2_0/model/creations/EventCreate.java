package kg.academia.academia_2_0.model.creations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
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
    private MultipartFile photo;
    private List<String> targetSkills;
}
