package kg.academia.academia_2_0.model.entities;

import jakarta.persistence.*;
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
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer costPerLesson;
    @ManyToOne
    private Branch branch;
    private Boolean active;
    private Date createDate;

    @PrePersist()
    public void onCreate() {
        this.createDate = Date.valueOf(LocalDate.now());
    }

    public boolean isMentalArithmetic() {
        return name.toLowerCase().equals("ментальная арифметика")
                || name.toLowerCase().equals("mental arithmetic");
    }
}
