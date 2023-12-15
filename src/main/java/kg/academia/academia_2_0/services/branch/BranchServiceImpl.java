package kg.academia.academia_2_0.services.branch;

import kg.academia.academia_2_0.model.creations.BranchCreate;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.users.Owner;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    private final BranchStorage branchStorage;
    private final ContextService contextService;
    private final UserStorage userStorage;
    private final Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    @Autowired
    public BranchServiceImpl(BranchStorage branchStorage, ContextService contextService, UserStorage userStorage) {
        this.branchStorage = branchStorage;
        this.contextService = contextService;
        this.userStorage = userStorage;
    }

    @Override
    public Branch createBranch(BranchCreate branchCreate) {
        return branchStorage.save(
                Branch.builder()
                        .state(branchCreate.getState())
                        .town(branchCreate.getTown())
                        .active(true)
                        .name(branchCreate.getName()).build());
    }

    @Override
    public void setOwner(Long branchId, Long ownerId) {
        Branch branch = branchStorage.getBranchById(branchId);
        Owner owner = userStorage.getOwner(ownerId);
        owner.addBranch(branch);
        userStorage.saveOwner(owner);
    }

    @Override
    public void changeStatus(Long branchId) {
        Branch branch = branchStorage.getBranchById(branchId);
        branch.setActive(!branch.getActive());
        branchStorage.save(branch);
    }

    @Override
    public List<Branch> findBranchesOfCurrentOwner() {
        return contextService.getCurrentOwner().getBranches();
    }

    @Override
    public Branch getBranchById(Long branchId) {
        return branchStorage.getBranchById(branchId);
    }
}
