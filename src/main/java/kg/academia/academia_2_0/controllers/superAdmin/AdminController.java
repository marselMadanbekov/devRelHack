package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.model.creations.UserCreate;
import kg.academia.academia_2_0.model.entities.users.Owner;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
