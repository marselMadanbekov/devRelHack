package kg.academia.academia_2_0.controllers.teacher;

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
@RequestMapping("/teacher/subjects")
public class TeacherSubjectsController {

    private final SubjectStorage subjectStorage;
    private final ContextService contextService;
    private final UserService userService;
    @Autowired
    public TeacherSubjectsController(SubjectStorage subjectStorage, ContextService contextService, UserService userService) {
        this.subjectStorage = subjectStorage;
        this.contextService = contextService;
        this.userService = userService;
    }

    @GetMapping()
    public String subjects(Model model) {
        Teacher teacher = contextService.getCurrentTeacher();
        List<Subject> subjects = subjectStorage.findAllByBranch(teacher.getBranch());
        model.addAttribute("subjects", subjects);
        return "teacher/subjects";
    }

    @GetMapping("/subject-details")
    public String subjectDetails(@RequestParam Long subjectId,
                                 Model model){
        Subject subject = subjectStorage.getSubjectById(subjectId);
        List<Teacher> teachers = userService.findTeachersBySubject(subject);
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);
        return "teacher/subject-details";
    }
}
