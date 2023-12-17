package kg.academia.academia_2_0.repositories.userRepos;

import kg.academia.academia_2_0.model.entities.users.Employee;
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
}
