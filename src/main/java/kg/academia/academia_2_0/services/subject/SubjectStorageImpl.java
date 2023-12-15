package kg.academia.academia_2_0.services.subject;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectStorageImpl implements SubjectStorage {
    private final SubjectRepository subjectRepository;


    @Autowired
    public SubjectStorageImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }



    @Override
    public Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public void deleteById(Long subjectId) {
        subjectRepository.delete(getSubjectById(subjectId));
    }

    @Override
    public List<Subject> findAllByBranch(Branch branch) {
        return subjectRepository.findAllByBranch(branch);
    }
}
