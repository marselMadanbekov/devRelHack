package kg.academia.academia_2_0.services.homework;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

public interface HomeWorkStorage {
    MentalHomeWork save(MentalHomeWork mentalHomeWork);
    MentalHomeWork getHomeWorkById(Long id);
    MentalExercise getExerciseById(Long id);
    void deleteById(Long id);

    List<MentalHomeWork> findHomeworksByGroupAndDate(Group group, Date valueOf);

    void deleteExerciseById(Long exerciseId);

    Page<MentalHomeWork> getHomeworksByGroupAndPage(Group group, Integer page);

    void saveExercise(MentalExercise mentalExercise);

    List<MentalHomeWork> findByPupil(Pupil pupil);

    MentalHomeWork findByExercise(MentalExercise exercise);
}
