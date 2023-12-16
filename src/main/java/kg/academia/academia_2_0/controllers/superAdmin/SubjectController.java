package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.services.subject.SubjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/super-admin/subjects")
public class SubjectController {

    private final SubjectStorage subjectStorage;

    @Autowired
    public SubjectController(SubjectStorage subjectStorage) {
        this.subjectStorage = subjectStorage;
    }

    @GetMapping
    public String subjects(Model model){
        List<Subject> subjects = subjectStorage.findAll();
        model.addAttribute("subjects", subjects);
        return "superadmin/subjects";
    }
}
