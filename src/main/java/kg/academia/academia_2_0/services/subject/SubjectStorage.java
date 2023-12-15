package kg.academia.academia_2_0.services.subject;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;

import java.util.List;

public interface SubjectStorage {

    Subject getSubjectById(Long subjectId);

    Subject save(Subject subject);

    List<Subject> findAll();

    void deleteById(Long subjectId);

    List<Subject> findAllByBranch(Branch branch);
}
