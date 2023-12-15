package kg.academia.academia_2_0.services.lesson;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface LessonStorage {
    void save(Lesson lesson);
    Lesson getById(Long id);
    List<Lesson> findByGroup(Group group);

    Optional<Lesson> findByGroupAndDate(Group group, Date date);
    List<Lesson> findByDate(Date date);

    List<Lesson> findByTeacher(Teacher teacher);

    void deleteById(Long id);

    List<Lesson> lastLessonsByGroupId(Long group);

    Page<Lesson> lessonsByGroupIdAndPage(Long groupId, Integer page);
}
