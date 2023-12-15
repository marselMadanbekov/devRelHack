package kg.academia.academia_2_0.services.lesson;

import kg.academia.academia_2_0.model.creations.LessonCreate;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.model.updations.AttendanceUpdate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LessonService {
    void createLesson(LessonCreate lessonCreate);
    List<Lesson> lastLessonsByGroupId(Long groupId);

    Lesson findLessonById(Long lessonId);

    void editLesson(AttendanceUpdate lessonCreate);

    Page<Lesson> lessonsByGroupIdAndPage(Long groupId, Integer page);
}
