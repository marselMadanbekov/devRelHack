package kg.academia.academia_2_0.services.mark;

import kg.academia.academia_2_0.model.entities.Mark;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.enums.MarkType;
import kg.academia.academia_2_0.model.enums.TrainerTask;
import kg.academia.academia_2_0.model.utilities.MarkDTO;
import kg.academia.academia_2_0.repositories.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MarkStorageImpl implements MarkStorage {
    private final MarkRepository markRepository;

    @Autowired
    public MarkStorageImpl(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    @Override
    public Mark save(Mark mark) {
        return markRepository.save(mark);
    }

    @Override
    public Mark getMarkById(Long id) {
        return markRepository.findById(id).orElseThrow();
        //:TODO add correct exception;
    }

    @Override
    public List<MarkDTO> getMarksBetweenDatesByMarkTypePupilAndSubject(Date startDate, Date endDate, Pupil pupil, Subject subject, MarkType markType) {
        return markRepository.getByDateRangeAndMarkTypePupilAndSubject(startDate, endDate, pupil, subject, markType);
    }

    @Override
    public void deleteById(Long id) {
        markRepository.deleteById(id);
    }

    @Override
    public Optional<Mark> findLastMarkBySubjectAndPupilAndTopicAndType(Subject subject, Pupil currentPupil, TrainerTask topic, MarkType homeWork) {
        return markRepository.findLastBySubjectAndPupilAndTopicAndType(subject, currentPupil, topic.getValue(), homeWork);
    }
    @Override
    public Optional<Mark> findLastMarkBySubjectAndPupilAndTopicAndType(Subject subject, Pupil currentPupil, String topic, MarkType homeWork) {
        return markRepository.findLastBySubjectAndPupilAndTopicAndType(subject, currentPupil, topic, homeWork);
    }
}
