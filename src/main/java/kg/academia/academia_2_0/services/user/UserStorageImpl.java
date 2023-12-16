package kg.academia.academia_2_0.services.user;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.repositories.userRepos.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
