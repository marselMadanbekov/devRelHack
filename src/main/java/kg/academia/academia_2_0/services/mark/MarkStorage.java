package kg.academia.academia_2_0.services.mark;

import kg.academia.academia_2_0.model.entities.Mark;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.enums.MarkType;
import kg.academia.academia_2_0.model.enums.TrainerTask;
import kg.academia.academia_2_0.model.utilities.MarkDTO;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface MarkStorage {
    Mark save(Mark mark);
    Mark getMarkById(Long id);

    List<MarkDTO> getMarksBetweenDatesByMarkTypePupilAndSubject(Date startDate, Date endDate, Pupil pupil, Subject subject, MarkType markType);
    void deleteById(Long id);

    Optional<Mark> findLastMarkBySubjectAndPupilAndTopicAndType(Subject subject, Pupil currentPupil, TrainerTask topic, MarkType homeWork);
    Optional<Mark> findLastMarkBySubjectAndPupilAndTopicAndType(Subject subject, Pupil currentPupil, String topic, MarkType homeWork);
}
