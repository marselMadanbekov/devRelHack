package kg.academia.academia_2_0.model.utilities;

import lombok.Data;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Data
public class MarkDTO {
    private Integer correct;
    private Integer incorrect;
    private String date;

    public MarkDTO(Long correct, Long incorrect, Date date) {
        this.correct = correct.intValue();
        this.incorrect = incorrect.intValue();
        this.date = new SimpleDateFormat("dd/MMMM").format(date);
    }
}
