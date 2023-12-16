package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/teacher/teachers")
public class TeacherTeachersController {

    private final GroupStorage groupStorage;
    private final ContextService contextService;
    private final UserStorage userStorage;
    @Autowired
    public TeacherTeachersController(GroupStorage groupStorage, ContextService contextService, UserStorage userStorage) {
        this.groupStorage = groupStorage;
        this.contextService = contextService;
        this.userStorage = userStorage;
    }

    @GetMapping
    public String teachers(Model model){
        Teacher teacher = contextService.getCurrentTeacher();
        List<Teacher> teachers = userStorage.findAllTeachersByBranch(teacher.getBranch());
        model.addAttribute("teachers", teachers);
        return "teacher/teachers";
    }

    @GetMapping("/teacher-details")
    public String teacherDetails(@RequestParam Long teacherId,
                                 Model model){
        Teacher teacher = userStorage.getTeacher(teacherId);
        List<Group> groups = groupStorage.findAllByTeacher(teacher);
        model.addAttribute("teacher", teacher);
        model.addAttribute("groups", groups);
        return "teacher/teacher-details";
    }
}
