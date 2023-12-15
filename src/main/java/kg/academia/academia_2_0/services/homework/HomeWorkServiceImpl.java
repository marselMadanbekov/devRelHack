package kg.academia.academia_2_0.services.homework;

import kg.academia.academia_2_0.model.creations.ExerciseCreate;
import kg.academia.academia_2_0.model.creations.HomeWorkCreate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Mark;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.enums.ExerciseType;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.mark.MarkService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.trainer.TrainerService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    private final HomeWorkStorage homeWorkStorage;
    private final TrainerService trainerService;
    private final UserStorage userStorage;
    private final GroupStorage groupStorage;
    private final ContextService contextService;
    private final MarkService markService;

    @Autowired
    public HomeWorkServiceImpl(HomeWorkStorage homeWorkStorage, TrainerService trainerService, UserStorage userStorage, GroupStorage groupStorage, ContextService contextService, MarkService markService) {
        this.homeWorkStorage = homeWorkStorage;
        this.trainerService = trainerService;
        this.userStorage = userStorage;
        this.groupStorage = groupStorage;
        this.contextService = contextService;
        this.markService = markService;
    }

    @Override
    public MentalHomeWork createHomeWork(HomeWorkCreate homeWorkCreate) {
        MentalHomeWork homeWork = new MentalHomeWork();
        homeWork.setPupil(userStorage.getPupil(homeWorkCreate.getPupilId()));
        homeWork.setDeadLine(homeWorkCreate.getDeadLine());
        homeWork.setGroup(groupStorage.getGroupById(homeWorkCreate.getGroupId()));
        return homeWorkStorage.save(homeWork);
    }

    @Override
    public List<MentalHomeWork> findCreatedHomeworksByGroupToday(Group group) {
        return homeWorkStorage.findHomeworksByGroupAndDate(group, Date.valueOf(LocalDate.now()));
    }

    @Override
    public List<Pupil> listOfPupilsToCreateHomework(List<MentalHomeWork> createdHomeworks, List<Pupil> pupils) {
        List<Pupil> pupilsToCreateHomeWork = new ArrayList<>();

        for (Pupil currentPupil : pupils) {
            boolean notCreated = true;
            for (MentalHomeWork current : createdHomeworks) {
                if (currentPupil.equals(current.getPupil())) {
                    notCreated = false;
                    break;
                }
            }
            if (notCreated)
                pupilsToCreateHomeWork.add(currentPupil);
        }
        return pupilsToCreateHomeWork;
    }

    @Override
    public MentalHomeWork findHomeworkById(Long homeworkId) {
        return homeWorkStorage.getHomeWorkById(homeworkId);
    }

    @Override
    @Transactional
    public void addExerciseToHomework(ExerciseCreate exerciseCreate) {
        MentalHomeWork mentalHomeWork = homeWorkStorage.getHomeWorkById(exerciseCreate.getHomeworkId());
        MentalExercise mentalExercise = exerciseCreateToExercise(exerciseCreate);
        mentalHomeWork.addExercise(mentalExercise);
        homeWorkStorage.saveExercise(mentalExercise);
        homeWorkStorage.save(mentalHomeWork);
    }

    @Override
    public void deleteExercise(Long exerciseId) {
        homeWorkStorage.deleteExerciseById(exerciseId);
    }

    @Override
    public Page<MentalHomeWork> getHomeworksPageByGroupAndPage(Group group, Integer page) {
        return homeWorkStorage.getHomeworksByGroupAndPage(group, page);
    }

    @Override
    public void deleteHomework(Long homeworkId) {
        homeWorkStorage.deleteById(homeworkId);
    }

    @Override
    public List<MentalHomeWork> homeworksByCurrentPupil() {
        Pupil pupil = contextService.getCurrentPupil();
        return homeWorkStorage.findByPupil(pupil);
    }

    @Override
    public MentalExercise findExerciseById(Long exerciseId) {
        return homeWorkStorage.getExerciseById(exerciseId);
    }

    @Override
    @Transactional
    public void submitTestResults(Integer totalQuestions, Integer correctAnswers, Long exerciseId) {
        MentalExercise exercise = homeWorkStorage.getExerciseById(exerciseId);
        exercise.addSolvedQuestions(totalQuestions);
        exercise.addCorrectQuestions(correctAnswers);
        homeWorkStorage.saveExercise(exercise);

        Mark lastMark = markService.findLastMarkByExercise(exercise);
        if (lastMark == null) {
            markService.createMarkByExercise(exercise, totalQuestions, correctAnswers);
        } else {
            lastMark.upCorrectAnswers(correctAnswers);
            lastMark.upTotalQuestions(totalQuestions);
            markService.save(lastMark);
        }
    }

    @Override
    @Transactional
    public MentalExercise submitTrainerResult(Long exerciseId, Boolean isCorrect) {
        MentalExercise mentalExercise = homeWorkStorage.getExerciseById(exerciseId);
        mentalExercise.addSolvedQuestions(1);
        mentalExercise.addCorrectQuestions(isCorrect ? 1 : 0);
        homeWorkStorage.saveExercise(mentalExercise);
        Mark lastMark = markService.findLastMarkByExercise(mentalExercise);
        if (lastMark == null) {
            markService.createMarkByExercise(mentalExercise, 1, isCorrect ? 1 : 0);
        } else {
            lastMark.upCorrectAnswers(isCorrect ? 1 : 0);
            lastMark.upTotalQuestions(1);
            markService.save(lastMark);
        }
        return mentalExercise;
    }

    @Override
    public void clearExerciseProgress(MentalExercise exercise) {
        exercise.clearProgress();
        homeWorkStorage.saveExercise(exercise);
    }

    private List<MentalExercise> mapExerciseCreateListToExerciseList(List<ExerciseCreate> exerciseCreates) {
        List<MentalExercise> mentalExercises = new ArrayList<>();
        for (ExerciseCreate exerciseCreate : exerciseCreates) {
            mentalExercises.add(exerciseCreateToExercise(exerciseCreate));
        }
        return mentalExercises;
    }

    private MentalExercise exerciseCreateToExercise(ExerciseCreate exerciseCreate) {

        return MentalExercise.builder()
                .digitsCount(exerciseCreate.getDigitsCount())
                .questionCount(exerciseCreate.getQuestionsCount())
                .numbersCount(exerciseCreate.getNumbersCount())
                .topic(trainerService.getTrainerTaskByName(exerciseCreate.getTopic()))
                .speed(exerciseCreate.getSpeed())
                .type(ExerciseType.values()[exerciseCreate.getType()])
                .build();
    }

}
