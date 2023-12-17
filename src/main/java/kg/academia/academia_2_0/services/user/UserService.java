package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    void createEmployee(EmployeeCreate employeeCreate);

    void updateUserdata(UserdataUpdate update);


    Page<Employee> getEmployeesByPage(Integer page);

    Employee findEmployeeById(Long userId);

    List<String> findUniqueSkills();

    void addSkill(Long employeeId, String skillName);

    void deleteSkill(Long employeeId, String skillName);

    void addSocMediaToEmployee(Long employeeId, String socMedia, String username);

    void deleteSocMedia(Long employeeId, String socMedia);

    void sendDistributionsByUser(Long userId, String message, List<String> targetDistributions);

    Page<Employee> getEmployeesByPageAndFilters(Integer page,Integer level,String skill);

}
