package kg.academia.academia_2_0.services.mark;

import kg.academia.academia_2_0.model.creations.GroupsRateData;
import kg.academia.academia_2_0.model.creations.MarkCreate;
import kg.academia.academia_2_0.model.creations.PupilsRate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Mark;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.enums.MarkType;
import kg.academia.academia_2_0.model.utilities.MarkDTO;
import kg.academia.academia_2_0.model.utilities.MultipleTrainerResults;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.homework.HomeWorkStorage;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class MarkServiceImpl implements MarkService {
    private final UserStorage userStorage;
    private final MarkStorage markStorage;
    private final SubjectStorage subjectStorage;
    private final ContextService contextService;
    private final HomeWorkStorage homeWorkStorage;
    private final GroupStorage groupStorage;

    @Autowired
    public MarkServiceImpl(UserStorage userStorage, MarkStorage markStorage, SubjectStorage subjectStorage, ContextService contextService, HomeWorkStorage homeWorkStorage, GroupStorage groupStorage) {
        this.userStorage = userStorage;
        this.markStorage = markStorage;
        this.subjectStorage = subjectStorage;
        this.contextService = contextService;
        this.homeWorkStorage = homeWorkStorage;
        this.groupStorage = groupStorage;
    }

    @Override
    public void createMark(MarkCreate markCreate) {
        markStorage.save(
                Mark.builder()
                        .topic(markCreate.getTopic())
                        .pupil(userStorage.getPupil(markCreate.getPupilId()))
                        .type(MarkType.values()[markCreate.getMarkType()])
                        .subject(subjectStorage.getSubjectById(markCreate.getSubjectId()))
                        .totalPoints(markCreate.getTotalPoints())
                        .earnedPoints(markCreate.getEarnedPoints())
                        .build());
    }

    @Override
    public List<MarkDTO> getHomeworkMarksByPupilAndSubject(Long pupilId, Long subjectId, Integer weekShift) {
        Pupil pupil = userStorage.getPupil(pupilId);
        Subject subject = subjectStorage.getSubjectById(subjectId);
        return markStorage.getMarksBetweenDatesByMarkTypePupilAndSubject(getTargetDateByWeekShift(weekShift + 1), getTargetDateByWeekShift(weekShift), pupil, subject, MarkType.HOME_WORK);
    }

    @Override
    public List<MarkDTO> getClassworkMarksByPupilAndSubject(Long pupilId, Long subjectId, Integer weekShift) {
        Pupil pupil = userStorage.getPupil(pupilId);
        Subject subject = subjectStorage.getSubjectById(subjectId);
        return markStorage.getMarksBetweenDatesByMarkTypePupilAndSubject(getTargetDateByWeekShift(weekShift + 1), getTargetDateByWeekShift(weekShift), pupil, subject, MarkType.CLASS_WORK);
    }

    @Override
    public List<MarkDTO> getIndependentWorkMarksByPupilAndSubject(Long pupilId, Long subjectId, Integer weekShift) {
        Pupil pupil = userStorage.getPupil(pupilId);
        Subject subject = subjectStorage.getSubjectById(subjectId);
        return markStorage.getMarksBetweenDatesByMarkTypePupilAndSubject(getTargetDateByWeekShift(weekShift + 1), getTargetDateByWeekShift(weekShift), pupil, subject, MarkType.INDEPENDENT_WORK);
    }

    @Override
    public Mark findLastMarkByExercise(MentalExercise exercise) {
        MentalHomeWork mentalHomeWork = homeWorkStorage.findByExercise(exercise);
        if (mentalHomeWork == null)
            throw new RuntimeException("Домашнее задание к которому привязано данное упрежнение не найдено!");
        return markStorage.findLastMarkBySubjectAndPupilAndTopicAndType(
                mentalHomeWork.getGroup().getSubject(),
                contextService.getCurrentPupil(),
                exercise.getTopic(),
                MarkType.HOME_WORK
        ).orElse(null);
    }

    @Override
    public void createMarkByExercise(MentalExercise exercise, Integer totalQuestions, Integer correctAnswers) {
        MentalHomeWork mentalHomeWork = homeWorkStorage.findByExercise(exercise);
        markStorage.save(Mark.builder()
                .type(MarkType.HOME_WORK)
                .topic(exercise.getTopic().getValue())
                .earnedPoints(correctAnswers)
                .totalPoints(totalQuestions)
                .subject(mentalHomeWork.getGroup().getSubject())
                .pupil(mentalHomeWork.getPupil())
                .build());
    }

    @Override
    public void save(Mark lastMark) {
        markStorage.save(lastMark);
    }

    @Override
    public void createIndependentWorkTrainerMark(String topic, Boolean isCorrect) {
        Pupil pupil = contextService.getCurrentPupil();
        createTrainerMarkByPupil(topic,isCorrect,MarkType.INDEPENDENT_WORK,pupil);
    }

    @Override
    public void createIndependentWorkTrainerMarkByPupil(String topic, Boolean isCorrect, Long pupilId) {
        Pupil pupil = userStorage.getPupil(pupilId);
        createTrainerMarkByPupil(topic,isCorrect,MarkType.CLASS_WORK,pupil);
    }

    @Override
    public void createMarksByMultipleTrainerResults(MultipleTrainerResults results) {
        if(results.getPupilId1() != null && results.getPupilId1() > 0){
            Pupil pupil = userStorage.getPupil(results.getPupilId1());
            createTrainerMarkByPupil(results.getTopic1(),results.getIsCorrect1(),MarkType.CLASS_WORK,pupil);
        }
        if(results.getPupilId2() != null && results.getPupilId2() > 0){
            Pupil pupil = userStorage.getPupil(results.getPupilId2());
            createTrainerMarkByPupil(results.getTopic2(),results.getIsCorrect2(),MarkType.CLASS_WORK,pupil);
        }
        if(results.getPupilId3() != null && results.getPupilId3() > 0){
            Pupil pupil = userStorage.getPupil(results.getPupilId3());
            createTrainerMarkByPupil(results.getTopic3(),results.getIsCorrect3(),MarkType.CLASS_WORK,pupil);
        }
        if(results.getPupilId4() != null && results.getPupilId4() > 0){
            Pupil pupil = userStorage.getPupil(results.getPupilId4());
            createTrainerMarkByPupil(results.getTopic4(),results.getIsCorrect4(),MarkType.CLASS_WORK,pupil);
        }

    }

    private void createTrainerMarkByPupil(String topic, boolean isCorrect,MarkType markType, Pupil pupil){
        List<Group> groups = groupStorage.findAllByPupil(pupil);
        for(Group group : groups){
            if(group.getSubject() != null && group.getSubject().isMentalArithmetic()){
                Mark mark = markStorage.findLastMarkBySubjectAndPupilAndTopicAndType(group.getSubject(),pupil,topic,MarkType.INDEPENDENT_WORK).orElse(new Mark());
                mark.upTotalQuestions(1);
                mark.upCorrectAnswers(isCorrect ? 1 : 0);
                mark.setTopic(topic);
                mark.setPupil(pupil);
                mark.setSubject(group.getSubject());
                mark.setType(markType);
                markStorage.save(mark);
            }
        }
    }

    private Date getTargetDateByWeekShift(Integer weekShift) {
        return Date.valueOf(LocalDate.now().minusWeeks(weekShift));
    }

    @Override
    public void createMarksToGroup(GroupsRateData requestData) {
        Subject subject = subjectStorage.getSubjectById(requestData.getSubjectId());
        for (PupilsRate pupilsRate : requestData.getPupilsRate()) {
            if (pupilsRate.getGrade() != 0 || (pupilsRate.getTheme() != null && !pupilsRate.getTheme().equals(""))) {
                markStorage.save(
                        Mark.builder()
                                .topic(pupilsRate.getTheme())
                                .pupil(userStorage.getPupil(pupilsRate.getPupilId()))
                                .type(MarkType.values()[requestData.getMarkType()])
                                .subject(subject)
                                .totalPoints(5)
                                .earnedPoints(pupilsRate.getGrade())
                                .build());
            }
        }
    }


}
