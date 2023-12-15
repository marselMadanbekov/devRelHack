package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Admin;
import kg.academia.academia_2_0.model.entities.users.Pupil;
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
@RequestMapping("/admin/groups")
public class AdminGroupController {
    private final UserStorage userStorage;
    private final GroupStorage groupStorage;
    private final SubjectStorage subjectStorage;
    private final ContextService contextService;

    @Autowired
    public AdminGroupController(UserStorage userStorage, GroupStorage groupStorage, SubjectStorage subjectStorage, ContextService contextService) {
        this.userStorage = userStorage;
        this.groupStorage = groupStorage;
        this.subjectStorage = subjectStorage;
        this.contextService = contextService;
    }

    @GetMapping
    public String main(Model model){
        Admin admin = contextService.getCurrentAdmin();
        List<Group> groups = groupStorage.findAllByBranch(admin.getBranch());
        model.addAttribute("groups", groups);
        return "admin/main";
    }
    @GetMapping("/group-details")
    public String groupDetails(@RequestParam Long groupId,
                               Model model){
        Admin admin = contextService.getCurrentAdmin();
        Group group = groupStorage.getGroupById(groupId);
        List<Subject> subjects = subjectStorage.findAllByBranch(admin.getBranch());
        List<Teacher> teachers = userStorage.findAllTeachersByBranch(admin.getBranch());
        model.addAttribute("group", group);
        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        return "admin/group-details";
    }
    @GetMapping("/add-pupils")
    public String addPupils(@RequestParam Long groupId,
                            Model model){
        Admin admin = contextService.getCurrentAdmin();
        Group group = groupStorage.getGroupById(groupId);
        List<Pupil> pupils = userStorage.findAllPupilsByBranch(admin.getBranch());

        model.addAttribute("group", group);
        model.addAttribute("pupils", pupils);
        return "admin/add-pupil";
    }

    @GetMapping("/create-group")
    public String createGroup(Model model){

        Admin admin = contextService.getCurrentAdmin();
        List<Teacher> teachers = userStorage.findAllTeachersByBranch(admin.getBranch());
        List<Subject> subjects = subjectStorage.findAllByBranch(admin.getBranch());

        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        model.addAttribute("branchId", admin.getBranch().getId());
        return "admin/create-group";
    }
}
