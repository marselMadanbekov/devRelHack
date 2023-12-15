package kg.academia.academia_2_0.model.entities.users;

import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.utilities.BranchManager;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin implements BranchManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    private UserData userData;
    @ManyToOne
    private Branch branch;


    @Override
    public boolean isManagerOfBranch(Branch branch) {
        return branch.equals(this.branch);
    }
}
