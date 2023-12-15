package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByDate(Date date);

    List<Lesson> findByGroup(Group group);

    List<Lesson> findByTeacher(Teacher teacher);

    List<Lesson> findTop3ByGroupIdOrderByDateDesc(Long groupId);

    Optional<Lesson> findByGroupAndDate(Group group, Date date);

    Page<Lesson> findByGroupId(Long groupId, Pageable pageable);
}
