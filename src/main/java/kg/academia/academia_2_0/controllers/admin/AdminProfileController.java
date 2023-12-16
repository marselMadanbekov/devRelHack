package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminProfileController {
    private final ContextService contextService;

    @Autowired
    public AdminProfileController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("/profile")
    public String profile(Model model){

        Employee employee = contextService.getCurrentEmployee();
        model.addAttribute("employee", employee);
        return "admin/profile";
    }
}
