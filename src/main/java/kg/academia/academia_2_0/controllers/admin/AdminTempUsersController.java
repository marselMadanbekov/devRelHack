package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/temp-users")
public class AdminTempUsersController {
    private final ContextService contextService;
    private final UserStorage userStorage;

    @Autowired
    public AdminTempUsersController(ContextService contextService, UserStorage userStorage) {
        this.contextService = contextService;
        this.userStorage = userStorage;
    }

    @GetMapping("")
    public String tempUsers(Model model){
        Admin admin = contextService.getCurrentAdmin();
        List<TempUser> tempUsers = userStorage.findAllTempUsersByBranch(admin.getBranch());
        model.addAttribute("tempUsers", tempUsers);
        return "admin/temp-users";
    }

    @GetMapping("/user-details")
    public String tempUserDetails(@RequestParam Long userId,
                                  Model model){
        TempUser tempUser = userStorage.getTempUser(userId);
        model.addAttribute("tempUser", tempUser);
        return "admin/temp-user-details";
    }
}
