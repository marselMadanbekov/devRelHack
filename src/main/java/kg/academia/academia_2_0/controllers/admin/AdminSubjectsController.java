package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Admin;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/subjects")
public class AdminSubjectsController {

    private final SubjectStorage subjectStorage;
    private final ContextService contextService;
    private final UserService userService;
    @Autowired
    public AdminSubjectsController(SubjectStorage subjectStorage, ContextService contextService, UserService userService) {
        this.subjectStorage = subjectStorage;
        this.contextService = contextService;
        this.userService = userService;
    }

    @GetMapping()
    public String subjects(Model model) {
        Admin admin = contextService.getCurrentAdmin();
        List<Subject> subjects = subjectStorage.findAllByBranch(admin.getBranch());
        model.addAttribute("subjects", subjects);
        return "admin/subjects";
    }

    @GetMapping("/subject-details")
    public String subjectDetails(@RequestParam Long subjectId,
                                 Model model){
        Subject subject = subjectStorage.getSubjectById(subjectId);
        List<Teacher> teachers = userService.findTeachersBySubject(subject);
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);
        return "admin/subject-details";
    }

    @GetMapping("/create-subject")
    public String createSubject(Model model) {
        Admin admin = contextService.getCurrentAdmin();
        model.addAttribute("branchId", admin.getBranch().getId());
        return "admin/create-subject";
    }

}
