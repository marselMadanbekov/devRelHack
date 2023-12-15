package kg.academia.academia_2_0.model.entities;


import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.enums.MarkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private int totalPoints;
    private int earnedPoints;
    @ManyToOne
    private Subject subject;
    private MarkType type;
    @ManyToOne
    private Pupil pupil;
    private String topic;

    @PrePersist()
    public void onCreate() {
        this.date = Date.valueOf(LocalDate.now());
    }

    public void upCorrectAnswers(int count) {
        earnedPoints += count;
    }

    public void upTotalQuestions(int count) {
        totalPoints += count;
    }

}
