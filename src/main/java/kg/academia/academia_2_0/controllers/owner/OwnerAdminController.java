package kg.academia.academia_2_0.controllers.owner;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/owner/admins")
public class OwnerAdminController {

    @GetMapping("/create-admin")
    public String createOwner(@RequestParam Long branchId,
                              Model model){
        model.addAttribute("branchId", branchId);
        return "owner/create-admin";
    }
}
