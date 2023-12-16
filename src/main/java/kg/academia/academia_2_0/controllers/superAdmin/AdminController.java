package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/super-admin/admins")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/create-admin")
    public String createOwner(@RequestParam Long branchId,
                              Model model){
        model.addAttribute("branchId", branchId);
        return "superadmin/create-admin";
    }

}
