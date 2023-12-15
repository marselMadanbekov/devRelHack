package kg.academia.academia_2_0.controllers.pupil;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.services.group.GroupService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pupil/profile")
public class PupilProfileController {
    private final ContextService contextService;
    private final GroupService groupService;
    private final SubjectStorage subjectStorage;

    @Autowired
    public PupilProfileController(ContextService contextService, GroupService groupService, SubjectStorage subjectStorage) {
        this.contextService = contextService;
        this.groupService = groupService;
        this.subjectStorage = subjectStorage;
    }

    @GetMapping()
    public String profile(Model model){
        Pupil pupil = contextService.getCurrentPupil();
        List<Group> groups = groupService.findGroupsByPupil(pupil);
        model.addAttribute("pupil", pupil);
        model.addAttribute("groups", groups);
        return "pupil/profile";
    }
    @GetMapping("/statistics")
    public String pupilStatistics(@RequestParam Long subjectId,
                                  Model model){
        Pupil pupil = contextService.getCurrentPupil();
        Subject subject = subjectStorage.getSubjectById(subjectId);
        model.addAttribute("pupil", pupil);
        model.addAttribute("subject",subject);
        return "pupil/pupil-statistics";
    }
}
