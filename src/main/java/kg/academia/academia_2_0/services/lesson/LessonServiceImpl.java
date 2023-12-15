package kg.academia.academia_2_0.services.lesson;

import kg.academia.academia_2_0.model.creations.LessonCreate;
import kg.academia.academia_2_0.model.creations.NotificationCreate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.model.updations.AttendanceUpdate;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonStorage lessonStorage;
    private final ContextService contextService;
    private final GroupStorage groupStorage;
    private final UserStorage userStorage;
    private final NotificationService notificationService;

    @Autowired
    public LessonServiceImpl(LessonStorage lessonStorage, ContextService contextService, GroupStorage groupStorage, UserStorage userStorage, NotificationService notificationService) {
        this.lessonStorage = lessonStorage;
        this.contextService = contextService;
        this.groupStorage = groupStorage;
        this.userStorage = userStorage;
        this.notificationService = notificationService;
    }

    @Override
    public void createLesson(LessonCreate lessonCreate) {
        Group group = groupStorage.getGroupById(lessonCreate.getGroupId());

        if (group.getSubject() == null)
            throw new RuntimeException("Нельзя создать урок если у группы нет предмета!");

        Teacher teacher = contextService.getCurrentTeacher();

        Lesson lesson = lessonStorage.findByGroupAndDate(group, Date.valueOf(lessonCreate.getDate())).orElse(null);
        if (lesson != null) {
            throw new RuntimeException("В эту дату у данной группы уже существует урок, если хотите изменить перейдите к целевому уроку!");
        } else
            lessonStorage.save(
                    Lesson.builder()
                            .date(Date.valueOf(lessonCreate.getDate()))
                            .teacher(teacher)
                            .group(group)
                            .attendance(mapLessonCreateAttendanceToRegularAndCountBalance(lessonCreate.getAttendance(), group.getSubject().getCostPerLesson()))
                            .build()
            );
    }
    @Override
    public List<Lesson> lastLessonsByGroupId(Long groupId) {
        return lessonStorage.lastLessonsByGroupId(groupId);
    }

    @Override
    public Lesson findLessonById(Long lessonId) {
        return lessonStorage.getById(lessonId);
    }

    @Override
    public void editLesson(AttendanceUpdate lessonCreate) {
        Lesson lesson = lessonStorage.getById(lessonCreate.getLessonId());
        if (lesson.getGroup().getSubject() == null)
            throw new RuntimeException("Нельзя изменить посещаемость для группы, у которой не обозначен предмет!");
        for (Pupil current : lesson.getAttendance().keySet()) {
            boolean isPresent = lessonCreate.getAttendance().getOrDefault(current.getId(), false);
            boolean isPresentAtLastVersion = lesson.getAttendance().get(current);

            if (isPresent == isPresentAtLastVersion)
                continue;
            else if (isPresent) {
                current.balanceDown(lesson.getGroup().getSubject().getCostPerLesson());
                if (current.getBalance() < 500)
                    notificationService.createLowBalanceNotification(current.getId());
            } else
                current.balanceUp(lesson.getGroup().getSubject().getCostPerLesson());
            lesson.upgradeAttendance(current, isPresent);
        }
        lessonStorage.save(lesson);
    }

    @Override
    public Page<Lesson> lessonsByGroupIdAndPage(Long groupId, Integer page) {
        return lessonStorage.lessonsByGroupIdAndPage(groupId, page);
    }

    private Map<Pupil, Boolean> mapLessonCreateAttendanceToRegularAndCountBalance(Map<Long, Boolean> request, int costOfLesson) {
        Map<Pupil, Boolean> attendance = new HashMap<>();
        for (Long id : request.keySet()) {
            Pupil pupil = userStorage.getPupil(id);
            boolean isPresent = request.get(id);
            if (isPresent)
                pupil.balanceDown(costOfLesson);
            if (pupil.getBalance() < 500) {
                notificationService.createLowBalanceNotification(pupil.getId());
            }
            attendance.put(pupil, isPresent);
        }
        return attendance;
    }
}
