package kg.academia.academia_2_0.repositories;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findAllByBranch(Branch branch);

    List<Group> findAllBySubject(Subject subject);

    List<Group> findAllByTeacher(Teacher teacher);

    List<Group> findAllByPupils(Pupil pupil);

    Optional<Group> findByName(String name);

    @Query("SELECT g.subject.name, COUNT(p) FROM Group g " +
            "JOIN g.pupils p " +
            "WHERE g.branch.id = :branchId " +
            "GROUP BY g.subject.name")
    List<Object[]> countPupilsBySubjectAndBranch(@Param("branchId") Long branchId);
}
