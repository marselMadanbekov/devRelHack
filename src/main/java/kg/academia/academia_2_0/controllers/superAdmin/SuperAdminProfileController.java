package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/super-admin")
public class SuperAdminProfileController {
    private final ContextService contextService;

    @Autowired
    public SuperAdminProfileController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("/profile")
    public String profile(Model model){
        SuperAdmin superAdmin = contextService.getCurrentSuperAdmin();
        model.addAttribute("superAdmin", superAdmin);
        return "superadmin/profile";
    }
}
