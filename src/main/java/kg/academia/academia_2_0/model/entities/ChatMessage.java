package kg.academia.academia_2_0.model.entities;


import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.users.Employee;
import lombok.Data;

@Entity
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chat chat;
    @ManyToOne
    private Employee from;
    @ManyToOne
    private Employee to;
}
