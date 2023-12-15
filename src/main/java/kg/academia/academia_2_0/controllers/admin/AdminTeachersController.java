package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Admin;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/teachers")
public class AdminTeachersController {

    private final GroupStorage groupStorage;
    private final ContextService contextService;
    private final UserStorage userStorage;
    private final SubjectStorage subjectStorage;
    @Autowired
    public AdminTeachersController(GroupStorage groupStorage, ContextService contextService, UserStorage userStorage, SubjectStorage subjectStorage) {
        this.groupStorage = groupStorage;
        this.contextService = contextService;
        this.userStorage = userStorage;
        this.subjectStorage = subjectStorage;
    }

    @GetMapping
    public String teachers(Model model){
        Admin admin = contextService.getCurrentAdmin();
        List<Teacher> teachers = userStorage.findAllTeachersByBranch(admin.getBranch());
        model.addAttribute("teachers", teachers);
        return "admin/teachers";
    }

    @GetMapping("/create-teacher")
    public String createTeacher(Model model){
        Admin admin = contextService.getCurrentAdmin();
        model.addAttribute("branchId", admin.getBranch().getId());
        return "admin/create-teacher";
    }
    @GetMapping("/teacher-details")
    public String teacherDetails(@RequestParam Long teacherId,
                                 Model model){
        Admin admin = contextService.getCurrentAdmin();
        Teacher teacher = userStorage.getTeacher(teacherId);
        List<Group> groups = groupStorage.findAllByTeacher(teacher);
        List<Subject> subjects = subjectStorage.findAllByBranch(admin.getBranch());
        model.addAttribute("teacher", teacher);
        model.addAttribute("groups", groups);
        model.addAttribute("subjects", subjects);
        return "admin/teacher-details";
    }
}
