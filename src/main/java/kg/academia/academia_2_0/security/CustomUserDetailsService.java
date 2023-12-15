package kg.academia.academia_2_0.security;

import kg.academia.academia_2_0.model.entities.users.UserData;
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
    public UserData loadUserByUsername(String username) throws UsernameNotFoundException {
        return build(userStorage.getUserDataByUsername(username));
    }

    public UserData loadUserById(Long id) {
        return build(userStorage.getUserDataById(id));
    }

    private UserData build(UserData userData) {
        return UserData.builder()
                .id(userData.getId())
                .username(userData.getUsername())
                .password(userData.getPassword())
                .role(userData.getRole())
                .active(userData.getActive())
                .build();
    }
}
