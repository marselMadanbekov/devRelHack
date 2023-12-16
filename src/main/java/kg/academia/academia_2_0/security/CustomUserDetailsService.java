package kg.academia.academia_2_0.security;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserStorage userStorage;

    @Autowired
    public CustomUserDetailsService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public Employee loadUserByUsername(String username) throws UsernameNotFoundException {
        return build(userStorage.getEmployeeByUsername(username));
    }

    public Employee loadUserById(Long id) {
        return build(userStorage.getEmployeeById(id));
    }

    private Employee build(Employee employee) {
        return Employee.builder()
                .id(employee.getId())
                .username(employee.getUsername())
                .password(employee.getPassword())
                .role(employee.getRole())
                .active(employee.getActive())
                .build();
    }
}
