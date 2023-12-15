package kg.academia.academia_2_0.controllers.pupil;

import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
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
@RequestMapping("/pupil/subjects")
public class PupilSubjectController {

    private final ContextService contextService;
    private final SubjectStorage subjectStorage;
    private final UserService userService;

    @Autowired
    public PupilSubjectController(ContextService contextService, SubjectStorage subjectStorage, UserService userService) {
        this.contextService = contextService;
        this.subjectStorage = subjectStorage;
        this.userService = userService;
    }

    @GetMapping()
    public String subjects(Model model) {
        Pupil pupil = contextService.getCurrentPupil();
        List<Subject> subjects = subjectStorage.findAllByBranch(pupil.getBranch());
        model.addAttribute("subjects", subjects);
        return "pupil/subjects";
    }

    @GetMapping("/subject-details")
    public String subjectDetails(@RequestParam Long subjectId,
                                 Model model){
        Subject subject = subjectStorage.getSubjectById(subjectId);
        List<Teacher> teachers = userService.findTeachersBySubject(subject);
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);
        return "pupil/subject-details";
    }
}
