package kg.academia.academia_2_0.model.entities;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Employee;
import lombok.Data;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Integer grade;
    @ManyToOne
    private Employee author;
    @ManyToOne
    private Event event;
}
