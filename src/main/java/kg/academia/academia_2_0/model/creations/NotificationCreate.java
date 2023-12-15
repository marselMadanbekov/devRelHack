package kg.academia.academia_2_0.model.creations;

import lombok.Data;

@Data
public class NotificationCreate {
    private String message;
    private String title;
    private Long userDataId;
}
