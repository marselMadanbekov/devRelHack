package kg.academia.academia_2_0.services.branch;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.repositories.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchStorageImpl implements BranchStorage {
    private final BranchRepository branchRepository;

    @Autowired
    public BranchStorageImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }


    @Override
    public Branch getBranchById(Long branchId) {
        return branchRepository.findById(branchId).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public Page<Branch> findAllByPageable(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }

    @Override
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    @Override
    public Branch save(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public void deleteById(Long branchId) {
        branchRepository.delete(getBranchById(branchId));
    }

}
