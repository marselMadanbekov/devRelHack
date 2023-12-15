package kg.academia.academia_2_0.controllers.owner;

import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.subject.SubjectService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/owner/subjects")
public class OwnerSubjectController {
    private final SubjectService subjectService;
    private final SubjectStorage subjectStorage;
    private final UserService userService;
    private final BranchStorage branchStorage;

    public OwnerSubjectController(SubjectService subjectService, SubjectStorage subjectStorage, UserService userService, BranchStorage branchStorage) {
        this.subjectService = subjectService;
        this.subjectStorage = subjectStorage;
        this.userService = userService;
        this.branchStorage = branchStorage;
    }

    @GetMapping
    public String subjects(@RequestParam Long branchId,
                           Model model){
        List<Subject> subjects = subjectService.findSubjectsByBranch(branchId);
        Branch branch = branchStorage.getBranchById(branchId);
        model.addAttribute("branch", branch);
        model.addAttribute("subjects", subjects);
        return "owner/subjects";
    }
    @GetMapping("/create-subject")
    public String createSubject(@RequestParam Long branchId,
                                Model model){
        model.addAttribute("branchId", branchId);
        return "owner/create-subject";
    }

    @GetMapping("/subject-details")
    public String subjectDetails(@RequestParam Long subjectId,
                                 Model model){
        Subject subject = subjectStorage.getSubjectById(subjectId);
        List<Teacher> teachers = userService.findTeachersBySubject(subject);
        model.addAttribute("subject", subject);
        model.addAttribute("teachers", teachers);
        return "owner/subject-details";
    }
}
