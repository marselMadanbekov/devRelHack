package kg.academia.academia_2_0.controllers.owner;

import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/owner/temp-users")
public class OwnerTempUserController {

    private final UserService userService;
    private final UserStorage userStorage;
    private final BranchStorage branchStorage;

    @Autowired
    public OwnerTempUserController(UserService userService, UserStorage userStorage, BranchStorage branchStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
        this.branchStorage = branchStorage;
    }

    @GetMapping()
    public String tempUser(@RequestParam Long branchId,
                           Model model){
        Branch branch = branchStorage.getBranchById(branchId);
        List<TempUser> tempUsers = userStorage.findAllTempUsersByBranch(branch);
        model.addAttribute("tempUsers", tempUsers);
        model.addAttribute("branch", branch);
        return "owner/temp-users";
    }

    @GetMapping("/user-details")
    public String tempUserDetails(@RequestParam Long userId,
                                  Model model){
        TempUser tempUser = userStorage.getTempUser(userId);
        model.addAttribute("tempUser", tempUser);
        return "owner/temp-user-details";
    }
}
