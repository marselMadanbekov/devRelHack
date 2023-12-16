package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.services.group.GroupService;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherProfileController {
    private final ContextService contextService;
    private final GroupService groupService;

    @Autowired
    public TeacherProfileController(ContextService contextService, GroupService groupService) {
        this.contextService = contextService;
        this.groupService = groupService;
    }

    @GetMapping("/profile")
    public String profile(Model model){
        Teacher teacher = contextService.getCurrentTeacher();
        List<Group> groups = groupService.findGroupsByTeacher(teacher);
        model.addAttribute("teacher", teacher);
        model.addAttribute("groups", groups);
        return "teacher/profile";
    }
}
