package kg.academia.academia_2_0.controllers.owner;

import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.group.GroupService;
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
@RequestMapping("/owner/groups")
public class OwnerGroupController {
    private final GroupService groupService;
    private final GroupStorage groupStorage;
    private final SubjectService subjectService;
    private final UserStorage userStorage;
    private final UserService userService;
    private final BranchStorage branchStorage;

    @Autowired
    public OwnerGroupController(GroupService groupService, GroupStorage groupStorage, SubjectService subjectService, UserStorage userStorage, UserService userService, BranchStorage branchStorage) {
        this.groupService = groupService;
        this.groupStorage = groupStorage;
        this.subjectService = subjectService;
        this.userStorage = userStorage;
        this.userService = userService;
        this.branchStorage = branchStorage;
    }

    @GetMapping
    public String groups(@RequestParam Long branchId,
                         Model model){
        List<Group> groups = groupService.findGroupsByBranch(branchId);
        Branch branch = branchStorage.getBranchById(branchId);

        model.addAttribute("branch", branch);
        model.addAttribute("groups", groups);
        return "owner/groups";
    }
    @GetMapping("/group-details")
    public String groupDetails(@RequestParam Long groupId,
                               Model model){
        Group group = groupStorage.getGroupById(groupId);
        List<Subject> subjects = subjectService.findSubjectsByBranch(group.getBranch().getId());
        List<Teacher> teachers = userService.findTeachersByBranch(group.getBranch().getId());
        model.addAttribute("group", group);
        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        return "owner/group-details";
    }

    @GetMapping("/create-group")
    public String createGroup(@RequestParam Long branchId,Model model){
        List<Teacher> teachers = userService.findTeachersByBranch(branchId);
        List<Subject> subjects = subjectService.findSubjectsByBranch(branchId);

        model.addAttribute("subjects", subjects);
        model.addAttribute("teachers", teachers);
        model.addAttribute("branchId", branchId);
        return "owner/create-group";
    }

    @GetMapping("/add-pupils")
    public String addPupils(@RequestParam Long groupId,
                            Model model){
        Group group = groupStorage.getGroupById(groupId);
        List<Pupil> pupils = userStorage.findAllPupilsByBranch(group.getBranch());

        model.addAttribute("group", group);
        model.addAttribute("pupils", pupils);
        return "owner/add-pupil";
    }
}
