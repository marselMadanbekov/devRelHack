package kg.academia.academia_2_0.services.branch;

import kg.academia.academia_2_0.model.entities.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BranchStorage {
    Branch getBranchById(Long branchId);
    Page<Branch> findAllByPageable(Pageable pageable);
    List<Branch> findAll();
    Branch save(Branch branch);

    void deleteById(Long branchId);
}
