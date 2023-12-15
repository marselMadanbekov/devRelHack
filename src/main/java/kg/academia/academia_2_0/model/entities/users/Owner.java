package kg.academia.academia_2_0.model.entities.users;


import jakarta.persistence.*;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.utilities.BranchManager;
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
public class Owner implements BranchManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.REMOVE)
    private UserData userData;
    @OneToMany
    private List<Branch> branches = new ArrayList<>();

    public void addBranch(Branch branch) {
        if (this.branches.contains(branch))
            return;
        this.branches.add(branch);
    }

    public Boolean isOwnerOfBranch(Long branchId) {
        for (Branch branch : branches)
            if (branch.getId().equals(branchId))
                return true;

        return false;
    }

    @Override
    public boolean isManagerOfBranch(Branch branch) {
        for(Branch current : this.branches)
            if(current.equals(branch))
                return true;
        return false;
    }
}
