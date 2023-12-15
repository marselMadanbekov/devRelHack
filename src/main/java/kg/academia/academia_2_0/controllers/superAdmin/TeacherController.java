package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/super-admin/teachers")
public class TeacherController {
    private final UserStorage userStorage;

    @Autowired
    public TeacherController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public String teachers(Model model){
        List<Teacher> teachers = userStorage.findAllTeachers();
        model.addAttribute("teachers", teachers);
        return "superadmin/teachers";
    }
}
