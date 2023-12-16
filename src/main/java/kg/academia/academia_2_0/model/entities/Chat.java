package kg.academia.academia_2_0.model.entities;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Employee;
import lombok.Data;

@Data
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee firstEmployee;
    @ManyToOne
    private Employee secondEmployee;
}

