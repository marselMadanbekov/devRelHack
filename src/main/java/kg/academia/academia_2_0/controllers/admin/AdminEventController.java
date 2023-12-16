package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/events")
public class AdminEventController {
    private final EventService eventService;

    @Autowired
    public AdminEventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public String lessonsHistory(@RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Page<Event> lessons = eventService.eventsByPage(page);
        model.addAttribute("lessons", lessons);
        return "admin/lessons-history";
    }

    @GetMapping("/lesson-details")
    public String lessonDetails(@RequestParam Long lessonId,
                                Model model){
        Event event = eventService.findEventById(lessonId);
        model.addAttribute("lesson", event);
        return "admin/lesson-details";
    }
}
