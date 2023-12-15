package kg.academia.academia_2_0.services.lesson;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.repositories.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LessonStorageImpl implements LessonStorage {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonStorageImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Override
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public List<Lesson> findByGroup(Group group) {
        return lessonRepository.findByGroup(group);
    }

    @Override
    public Optional<Lesson> findByGroupAndDate(Group group, Date date) {
        return lessonRepository.findByGroupAndDate(group, date);
    }

    @Override
    public List<Lesson> findByDate(Date date) {
        return lessonRepository.findByDate(date);
    }

    @Override
    public List<Lesson> findByTeacher(Teacher teacher) {
        return lessonRepository.findByTeacher(teacher);
    }

    @Override
    public void deleteById(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public List<Lesson> lastLessonsByGroupId(Long groupId) {
        return lessonRepository.findTop3ByGroupIdOrderByDateDesc(groupId);
    }

    @Override
    public Page<Lesson> lessonsByGroupIdAndPage(Long groupId, Integer page) {
        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC,"date"));
        return lessonRepository.findByGroupId(groupId, pageable);
    }
}
