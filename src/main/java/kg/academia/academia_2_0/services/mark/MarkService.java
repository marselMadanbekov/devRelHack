package kg.academia.academia_2_0.services.mark;

import kg.academia.academia_2_0.model.creations.GroupsRateData;
import kg.academia.academia_2_0.model.creations.MarkCreate;
import kg.academia.academia_2_0.model.entities.Mark;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.utilities.MarkDTO;
import kg.academia.academia_2_0.model.utilities.MultipleTrainerResults;

import java.util.List;

public interface MarkService {
    void createMark(MarkCreate markCreate);
    List<MarkDTO> getHomeworkMarksByPupilAndSubject(Long pupilId,Long subjectId, Integer weekShift);

    List<MarkDTO> getClassworkMarksByPupilAndSubject(Long pupilId, Long subjectId, Integer weekShift);

    void createMarksToGroup(GroupsRateData requestData);

    List<MarkDTO> getIndependentWorkMarksByPupilAndSubject(Long pupilId, Long subjectId, Integer weekShift);

    Mark findLastMarkByExercise(MentalExercise exercise);

    void createMarkByExercise(MentalExercise exercise, Integer totalQuestions, Integer correctAnswers);

    void save(Mark lastMark);

    void createIndependentWorkTrainerMark(String topic, Boolean isCorrect);

    void createIndependentWorkTrainerMarkByPupil(String topic, Boolean isCorrect, Long pupilId);

    void createMarksByMultipleTrainerResults(MultipleTrainerResults results);
}
