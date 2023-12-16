package kg.academia.academia_2_0.model.entities.users;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Data
@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Employee creator;
    private String message;
    @ElementCollection
    private Map<Employee,Integer> votes;
}
