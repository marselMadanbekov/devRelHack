package kg.academia.academia_2_0.services.security;

import kg.academia.academia_2_0.model.entities.users.*;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ContextService {
    private final UserStorage userStorage;

    @Autowired
    public ContextService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public String getUsernameOfCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    public Employee getCurrentEmployee(){
        return userStorage.getEmployeeByUsername(getUsernameOfCurrentUser());
    }


}
