package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.entities.users.*;
import kg.academia.academia_2_0.model.enums.Level;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.List;

public interface UserStorage {
    Employee getEmployeeById(Long id);
    Employee getEmployeeByUsername(String username);

    List<Employee> findAllEmployeesByPage();

    Employee saveEmployee(Employee tempUser);

    void deleteEmployeeById(Long id);

    Page<Employee> getEmployeesByPage(Integer page);

    List<String> findUniqueSkills();

    List<Employee> findAllBySkills(List<String> targetSkills);

    Page<Employee> getEmployeesBySkillAndPage(String skill, Integer page);

    Page<Employee> getEmployeesByLevelAndPage(Level value, Integer page);

    Page<Employee> getEmployeesByLevelAndSkillAndPage(Level value, String skill, Integer page);
}
