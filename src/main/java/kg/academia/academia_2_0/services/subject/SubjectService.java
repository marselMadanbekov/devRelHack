package kg.academia.academia_2_0.services.subject;

import kg.academia.academia_2_0.model.creations.SubjectCreate;
import kg.academia.academia_2_0.model.entities.Subject;

import java.util.List;

public interface SubjectService {
    Subject createSubject(SubjectCreate subjectCreate);

    List<Subject> findAll();

    List<Subject> findSubjectsByBranch(Long branchId);

    void changeStatus(Long subjectId);

    void updateSubject(Long subjectId, String name, Integer costPerLesson);
}
