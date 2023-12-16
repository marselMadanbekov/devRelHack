package kg.academia.academia_2_0.controllers.pupil;

import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pupil/groups")
public class PupilGroupController {
    private final ContextService contextService;
    private final GroupStorage groupStorage;

    @Autowired
    public PupilGroupController(ContextService contextService, GroupStorage groupStorage) {
        this.contextService = contextService;
        this.groupStorage = groupStorage;
    }

    @GetMapping
    public String main(Model model){
        Pupil pupil = contextService.getCurrentPupil();
        List<Group> groups = groupStorage.findAllByPupil(pupil);
        model.addAttribute("groups", groups);
        return "pupil/main";
    }

    @GetMapping("/group-details")
    public String groupDetails(@RequestParam Long groupId,
                               Model model){
        Group group = groupStorage.getGroupById(groupId);
        model.addAttribute("group", group);
        return "pupil/group-details";
    }
}
