package kg.academia.academia_2_0.services.branch;

import kg.academia.academia_2_0.model.creations.BranchCreate;
import kg.academia.academia_2_0.model.entities.Branch;

import java.util.List;

public interface BranchService {
    Branch createBranch(BranchCreate branchCreate);

    void setOwner(Long branchId, Long ownerId);

    void changeStatus(Long branchId);

    List<Branch> findBranchesOfCurrentOwner();

    Branch getBranchById(Long branchId);
}
