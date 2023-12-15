package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/super-admin/pupils")
public class PupilController {
    private final UserStorage userStorage;

    @Autowired
    public PupilController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public String pupils(Model model){
        List<Pupil> pupils = userStorage.findAllPupils();
        model.addAttribute("pupils", pupils);
        return "superadmin/pupils";
    }
}
