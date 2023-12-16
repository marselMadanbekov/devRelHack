package kg.academia.academia_2_0.services.security;

import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessGuardService {
    private final ContextService contextService;
    private final UserStorage userStorage;

    @Autowired
    public AccessGuardService(ContextService contextService, UserStorage userStorage) {
        this.contextService = contextService;
        this.userStorage = userStorage;
    }


}
