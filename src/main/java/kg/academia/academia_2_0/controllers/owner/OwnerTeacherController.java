package kg.academia.academia_2_0.controllers.owner;

import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.subject.SubjectService;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/owner/teachers")
public class OwnerTeacherController {
    private final UserService userService;
    private final UserStorage userStorage;
    private final BranchStorage branchStorage;
    private final GroupStorage groupStorage;
    private final SubjectService subjectService;

    @Autowired
    public OwnerTeacherController(UserService userService, UserStorage userStorage, BranchStorage branchStorage, GroupStorage groupStorage, SubjectService subjectService) {
        this.userService = userService;
        this.userStorage = userStorage;
        this.branchStorage = branchStorage;
        this.groupStorage = groupStorage;
        this.subjectService = subjectService;
    }

    @GetMapping()
    public String teachers(@RequestParam Long branchId,
                           Model model){
        List<Teacher> teachers = userService.findTeachersByBranch(branchId);
        Branch branch = branchStorage.getBranchById(branchId);

        model.addAttribute("branch", branch);
        model.addAttribute("teachers", teachers);
        return "owner/teachers";
    }
    @GetMapping("/teacher-details")
    public String teacherDetails(@RequestParam Long teacherId,
                                 @RequestParam Long branchId,
                                 Model model){
        Teacher teacher = userStorage.getTeacher(teacherId);
        List<Group> groups = groupStorage.findAllByTeacher(teacher);
        List<Subject> subjects = subjectService.findSubjectsByBranch(branchId);
        model.addAttribute("teacher", teacher);
        model.addAttribute("groups", groups);
        model.addAttribute("subjects", subjects);
        return "owner/teacher-details";
    }
    @GetMapping("/create-teacher")
    public String createTeacher(@RequestParam Long branchId,
                                Model model){
        model.addAttribute("branchId", branchId);
        return "owner/create-teacher";
    }
}
