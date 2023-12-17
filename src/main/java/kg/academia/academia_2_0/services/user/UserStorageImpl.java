package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.enums.Level;
import kg.academia.academia_2_0.repositories.userRepos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserStorageImpl implements UserStorage {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserStorageImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }

    @Override
    public List<Employee> findAllEmployeesByPage() {
        return Collections.emptyList();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> getEmployeesByPage(Integer page) {
        Pageable pageable = PageRequest.of(page,15);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public List<String> findUniqueSkills() {
        return employeeRepository.findUniqueSkills();
    }

    @Override
    public List<Employee> findAllBySkills(List<String> targetSkills) {
        return employeeRepository.findEmployeesBySkillsIn(targetSkills);
    }

    @Override
    public Page<Employee> getEmployeesBySkillAndPage(String skill, Integer page) {
        Pageable pageable = PageRequest.of(page,15);
        return employeeRepository.findAllBySkillsContaining(skill,pageable);
    }

    @Override
    public Page<Employee> getEmployeesByLevelAndPage(Level value, Integer page) {
        Pageable pageable = PageRequest.of(page,15);
        return employeeRepository.findAllByLevel(value,pageable);
    }

    @Override
    public Page<Employee> getEmployeesByLevelAndSkillAndPage(Level value, String skill, Integer page) {
        Pageable pageable = PageRequest.of(page,15);
        return employeeRepository.findAllByLevelAndSkillsContaining(value,skill,pageable);
    }
}
