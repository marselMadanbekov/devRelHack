package kg.academia.academia_2_0.model.entities.users;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    private UserData userData;
    @ManyToMany
    private List<Subject> subjects = new ArrayList<>();
    @ManyToOne
    private Branch branch;

    public void addSubject(Subject subject) {
        if(subjects.contains(subject))
            throw new RuntimeException("Учитель уже ведет этот предмет");
        subjects.add(subject);
    }

    public void deleteSubject(Subject subject) {
        subjects.remove(subject);
    }
}
