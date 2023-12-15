package kg.academia.academia_2_0.services.group;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.model.utilities.ChartTuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupStorage {
    Group getGroupById(Long id);
    Page<Group> findAllByPageable(Pageable pageable);
    List<Group> findAll();
    List<Group> findAllByBranch(Branch branch);
    List<Group> findAllBySubject(Subject subject);
    List<Group> findAllByTeacher(Teacher teacher);

    List<Group> findAllByPupil(Pupil pupil);
    Group findByName(String name);
    void save(Group group);
    void deleteById(Long id);

    List<ChartTuple> getPupilsCountBySubjectAndBranch(Long branchId);
}
