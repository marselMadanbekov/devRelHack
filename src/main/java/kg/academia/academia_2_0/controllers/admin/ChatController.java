package kg.academia.academia_2_0.controllers.admin;


import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat/test")
public class ChatController {
    private final ContextService contextService;

    @Autowired
    public ChatController(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("")
    public String tempUsers(Model model){
        Employee userData = contextService.getCurrentEmployee();
        model.addAttribute("userData", userData);
        return "chat/index";
    }
}
