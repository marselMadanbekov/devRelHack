package kg.academia.academia_2_0.controllers.tempUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/temp-user")
public class TempUserController {

    @GetMapping
    public String tempUserPage(){
        return "tempUser/temp-user-page";
    }
}
