package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.model.creations.GroupsRateData;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.users.Teacher;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.mark.MarkService;
import kg.academia.academia_2_0.services.security.ContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/groups")
public class TeacherGroupController {
    private final GroupStorage groupStorage;
    private final MarkService markService;
    private final ContextService contextService;

    @Autowired
    public TeacherGroupController(GroupStorage groupStorage, MarkService markService, ContextService contextService) {
        this.groupStorage = groupStorage;
        this.markService = markService;
        this.contextService = contextService;
    }

    @GetMapping
    public String main(Model model) {
        Teacher teacher = contextService.getCurrentTeacher();
        List<Group> groups = groupStorage.findAllByTeacher(teacher);
        model.addAttribute("groups", groups);
        return "teacher/main";
    }

    @GetMapping("/rate")
    public String rate(@RequestParam Long groupId,
                       Model model) {
        Group group = groupStorage.getGroupById(groupId);
        model.addAttribute("group", group);
        return "teacher/rating";
    }

    @PostMapping("/submit-rate")
    public ResponseEntity<Map<String, String>> saveData(@RequestBody GroupsRateData requestData) {

        Map<String, String> response = new HashMap<>();
        try {
            markService.createMarksToGroup(requestData);
            response.put("message", "Оценки успешно сохранены!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/group-details")
    public String groupDetails(@RequestParam Long groupId,
                               Model model) {
        Group group = groupStorage.getGroupById(groupId);
        model.addAttribute("group", group);
        return "teacher/group-details";
    }
}
