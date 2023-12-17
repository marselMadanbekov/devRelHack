package kg.academia.academia_2_0.repositories.userRepos;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String name);

    @Query("SELECT DISTINCT e.skills FROM Employee e")
    List<String> findUniqueSkills();

    @Query("SELECT e FROM Employee e WHERE EXISTS (SELECT s FROM e.skills s WHERE s IN :targetSkills)")
    List<Employee> findEmployeesBySkillsIn(List<String> targetSkills);

    Page<Employee> findAllBySkillsContaining(String skill, Pageable pageable);

    Page<Employee> findAllByLevel(Level value, Pageable pageable);

    Page<Employee> findAllByLevelAndSkillsContaining(Level value, String skill, Pageable pageable);
}
