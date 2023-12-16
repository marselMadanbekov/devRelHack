package kg.academia.academia_2_0.controllers.admin;

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
@RequestMapping("/admin/pupils")
public class AdminPupilsController {
    private final UserStorage userStorage;
    private final ContextService contextService;
    private final GroupStorage groupStorage;
    private final SubjectStorage subjectStorage;

    @Autowired
    public AdminPupilsController(UserStorage userStorage, ContextService contextService, GroupStorage groupStorage, SubjectStorage subjectStorage) {
        this.userStorage = userStorage;
        this.contextService = contextService;
        this.groupStorage = groupStorage;
        this.subjectStorage = subjectStorage;
    }

    @GetMapping
    public String pupils(Model model){
        Admin admin = contextService.getCurrentAdmin();
        List<Pupil> pupils = userStorage.findAllPupilsByBranch(admin.getBranch());
        model.addAttribute("pupils", pupils);
        return "admin/pupils";
    }

    @GetMapping("/pupil-details")
    public String pupilDetails(@RequestParam Long pupilId,
                               Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        List<Group> groups = groupStorage.findAllByPupil(pupil);
        model.addAttribute("pupil", pupil);
        model.addAttribute("groups", groups);
        return "admin/pupil-details";
    }

    @GetMapping("/create-pupil")
    public String createPupil(Model model){
        Admin admin = contextService.getCurrentAdmin();
        model.addAttribute("branchId", admin.getBranch().getId());
        return "admin/create-pupil";
    }
    @GetMapping("/pupil-statistics")
    public String pupilStatistics(@RequestParam Long pupilId,
                                  @RequestParam Long subjectId,
                                  Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        Subject subject = subjectStorage.getSubjectById(subjectId);
        model.addAttribute("pupil", pupil);
        model.addAttribute("subject",subject);
        return "admin/pupil-statistics";
    }

}
