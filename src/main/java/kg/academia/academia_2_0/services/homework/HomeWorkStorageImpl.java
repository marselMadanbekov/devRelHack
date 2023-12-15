package kg.academia.academia_2_0.services.homework;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.repositories.ExerciseRepository;
import kg.academia.academia_2_0.repositories.HomeWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class HomeWorkStorageImpl implements HomeWorkStorage {
    private final HomeWorkRepository homeWorkRepository;
    private final ExerciseRepository exerciseRepository;

    @Autowired
    public HomeWorkStorageImpl(HomeWorkRepository homeWorkRepository, ExerciseRepository exerciseRepository) {
        this.homeWorkRepository = homeWorkRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public MentalHomeWork save(MentalHomeWork mentalHomeWork) {
        return homeWorkRepository.save(mentalHomeWork);
    }

    @Override
    public MentalHomeWork getHomeWorkById(Long id) {
        return homeWorkRepository.findById(id).orElseThrow();
        //:TODO add correct exception;
    }

    @Override
    public MentalExercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElseThrow();
        //:TODO add correct exception;
    }

    @Override
    public void deleteById(Long id) {
        homeWorkRepository.deleteById(id);
    }

    @Override
    public List<MentalHomeWork> findHomeworksByGroupAndDate(Group group, Date date) {
        return homeWorkRepository.findByGroupAndCreateDate(group, date);
    }

    @Override
    public void deleteExerciseById(Long exerciseId) {
        exerciseRepository.deleteById(exerciseId);
    }

    @Override
    public Page<MentalHomeWork> getHomeworksByGroupAndPage(Group group, Integer page) {
        Pageable pageable = PageRequest.of(page,6, Sort.by(Sort.Direction.DESC, "createDate"));
        return homeWorkRepository.findByGroup(group,pageable);
    }

    @Override
    public void saveExercise(MentalExercise mentalExercise) {
        exerciseRepository.save(mentalExercise);
    }

    @Override
    public List<MentalHomeWork> findByPupil(Pupil pupil) {
        return homeWorkRepository.findByPupil(pupil);
    }

    @Override
    public MentalHomeWork findByExercise(MentalExercise exercise) {
        return homeWorkRepository.findByMentalExercises(exercise).orElse(null);
    }
}
