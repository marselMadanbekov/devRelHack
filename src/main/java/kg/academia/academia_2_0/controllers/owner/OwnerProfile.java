package kg.academia.academia_2_0.controllers.owner;

import kg.academia.academia_2_0.model.entities.users.Owner;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/owner")
public class OwnerProfile {
    private final ContextService contextService;

    @Autowired
    public OwnerProfile(ContextService contextService) {
        this.contextService = contextService;
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Owner owner = contextService.getCurrentOwner();
        model.addAttribute("owner", owner);
        return "owner/profile";
    }
}
