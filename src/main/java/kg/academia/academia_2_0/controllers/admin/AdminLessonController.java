package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/lessons")
public class AdminLessonController {
    private final GroupStorage groupStorage;
    private final EventService eventService;

    @Autowired
    public AdminLessonController(GroupStorage groupStorage, EventService eventService) {
        this.groupStorage = groupStorage;
        this.eventService = eventService;
    }

    @GetMapping("/lessons-history")
    public String lessonsHistory(@RequestParam Long groupId,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Group group = groupStorage.getGroupById(groupId);
        Page<Event> lessons = eventService.lessonsByGroupIdAndPage(groupId, page);
        model.addAttribute("group", group);
        model.addAttribute("lessons", lessons);
        return "admin/lessons-history";
    }

    @GetMapping("/lesson-details")
    public String lessonDetails(@RequestParam Long lessonId,
                                Model model){
        Event event = eventService.findLessonById(lessonId);
        model.addAttribute("lesson", event);
        return "admin/lesson-details";
    }
}
