package kg.academia.academia_2_0.services.group;

import kg.academia.academia_2_0.model.creations.GroupCreate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;

import java.util.List;

public interface GroupService {
    void createGroup(GroupCreate groupCreate);

    void addPupilsToGroup(Long groupId, List<Long> pupils);

    void removePupil(Long groupId, Long pupilId);

    void updateGroup(Long groupId, String name, Long subjectId, Long teacherId);

    void addLessonToTimetable(Long groupId, Integer dayOfWeek, String clock);

    void deleteLessonDay(Long groupId, Integer dayOfWeek);

    void changeStatus(Long groupId);

    List<Group> findGroupsByBranch(Long branchId);

    List<Group> findGroupsByTeacher(Teacher teacher);

    List<Group> findGroupsByPupil(Pupil pupil);
}
