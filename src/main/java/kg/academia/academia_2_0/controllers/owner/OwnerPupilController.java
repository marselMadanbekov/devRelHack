package kg.academia.academia_2_0.controllers.owner;


import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
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
@RequestMapping("/owner/pupils")
public class OwnerPupilController {
    private final UserService userService;
    private final UserStorage userStorage;
    private final BranchStorage branchStorage;
    private final GroupStorage groupStorage;
    private final SubjectStorage subjectStorage;

    @Autowired
    public OwnerPupilController(UserService userService, UserStorage userStorage, BranchStorage branchStorage, GroupStorage groupStorage, SubjectStorage subjectStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
        this.branchStorage = branchStorage;
        this.groupStorage = groupStorage;
        this.subjectStorage = subjectStorage;
    }

    @GetMapping()
    public String pupils(@RequestParam Long branchId,
                           Model model){
        List<Pupil> pupils = userService.findPupilsByBranch(branchId);
        Branch branch = branchStorage.getBranchById(branchId);

        model.addAttribute("branch", branch);
        model.addAttribute("pupils", pupils);
        return "owner/pupils";
    }
    @GetMapping("/pupil-details")
    public String pupilDetails(@RequestParam Long pupilId,
                               Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        List<Group> groups = groupStorage.findAllByPupil(pupil);
        model.addAttribute("pupil", pupil);
        model.addAttribute("groups", groups);
        return "owner/pupil-details";
    }
    @GetMapping("/pupil-statistics")
    public String pupilStatistics(@RequestParam Long pupilId,
                                  @RequestParam Long subjectId,
                                  Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        Subject subject = subjectStorage.getSubjectById(subjectId);
        model.addAttribute("pupil", pupil);
        model.addAttribute("subject",subject);
        return "owner/pupil-statistics";
    }
    @GetMapping("/create-pupil")
    public String createPupil(@RequestParam Long branchId,
                              Model model){
        model.addAttribute("branchId", branchId);
        return "owner/create-pupil";
    }
}
