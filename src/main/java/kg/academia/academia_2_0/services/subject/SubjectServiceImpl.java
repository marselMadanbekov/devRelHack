package kg.academia.academia_2_0.services.subject;

import kg.academia.academia_2_0.model.creations.SubjectCreate;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.security.AccessGuardService;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{
    private final BranchStorage branchStorage;
    private final SubjectStorage subjectStorage;
    private final AccessGuardService accessGuardService;
    private final ContextService contextService;

    @Autowired
    public SubjectServiceImpl(BranchStorage branchStorage, SubjectStorage subjectStorage, AccessGuardService accessGuardService, ContextService contextService) {
        this.branchStorage = branchStorage;
        this.subjectStorage = subjectStorage;
        this.accessGuardService = accessGuardService;
        this.contextService = contextService;
    }

    @Override
    public Subject createSubject(SubjectCreate subjectCreate) {
        if (accessGuardService.isDeniedToCRUDBranchEntities(subjectCreate.getBranchId()))
            throw new AccessDeniedException("Вы не можете создавать предмет для данного филиала!");
        Branch branch = branchStorage.getBranchById(subjectCreate.getBranchId());
        return subjectStorage.save(Subject.builder()
                .name(subjectCreate.getName())
                .branch(branch)
                .active(true)
                .costPerLesson(subjectCreate.getCostPerLesson()).build());
    }

    @Override
    public List<Subject> findAll() {
        return subjectStorage.findAll();
    }

    @Override
    public List<Subject> findSubjectsByBranch(Long branchId) {
        Branch branch = branchStorage.getBranchById(branchId);
        return subjectStorage.findAllByBranch(branch);
    }

    @Override
    public void changeStatus(Long subjectId) {
        Subject subject = subjectStorage.getSubjectById(subjectId);

        if(accessGuardService.isDeniedToCRUDBranchEntities(subject.getBranch().getId()))
            throw new AccessDeniedException("У вас недостаточно прав для изменения данного предмета!");

        subject.setActive(!subject.getActive());
        subjectStorage.save(subject);
    }

    @Override
    public void updateSubject(Long subjectId, String name, Integer costPerLesson) {
        Subject subject = subjectStorage.getSubjectById(subjectId);
        if(accessGuardService.isDeniedToCRUDBranchEntities(subject.getBranch().getId()))
            throw new AccessDeniedException("У вас недостаточно прав для изменения данного предмета!");

        if(name != null && !name.isEmpty())
            subject.setName(name);

        if(costPerLesson != null && costPerLesson > 0)
            subject.setCostPerLesson(costPerLesson);

        subjectStorage.save(subject);
    }


}
