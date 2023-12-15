package kg.academia.academia_2_0.services.homework;


import kg.academia.academia_2_0.model.creations.ExerciseCreate;
import kg.academia.academia_2_0.model.creations.HomeWorkCreate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HomeWorkService {
    MentalHomeWork createHomeWork(HomeWorkCreate homeWorkCreate);

    List<MentalHomeWork> findCreatedHomeworksByGroupToday(Group group);

    List<Pupil> listOfPupilsToCreateHomework(List<MentalHomeWork> createdHomeworks, List<Pupil> pupils);

    MentalHomeWork findHomeworkById(Long homeworkId);

    void addExerciseToHomework(ExerciseCreate exerciseCreate);

    void deleteExercise(Long exerciseId);

    Page<MentalHomeWork> getHomeworksPageByGroupAndPage(Group group, Integer page);

    void deleteHomework(Long homeworkId);

    List<MentalHomeWork> homeworksByCurrentPupil();

    MentalExercise findExerciseById(Long exerciseId);

    void submitTestResults(Integer totalQuestions, Integer correctAnswers, Long exerciseId);

    MentalExercise submitTrainerResult(Long exerciseId, Boolean isCorrect);

    void clearExerciseProgress(MentalExercise exercise);
}
