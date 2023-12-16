package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.services.event.EventService;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    private final UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping()
    public String events(@RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Page<Event> events = eventService.eventsByPage(page);
        model.addAttribute("events", events);
        return "main";
    }

    @GetMapping("/event-details")
    public String eventDetails(@RequestParam Long eventId,
                               Model model){
        Event event = eventService.findEventById(eventId);
        model.addAttribute("event", event);
        return "event-details";
    }
    @GetMapping("/create-event")
    public String createEvent(Model model){
        List<String> skills = userService.findUniqueSkills();
        model.addAttribute("skills", skills);
        return "create-event";
    }
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerToEvent(@RequestParam Long eventId){
        Map<String,String> response = new HashMap<>();
        try{
            eventService.registerToEventCurrentUser(eventId);
            response.put("message", "Вы успешно зарегистрированы!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/create-event")
    public ResponseEntity<Map<String, String>> createProduct(@ModelAttribute EventCreate eventCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            eventService.createEvent(eventCreate);
            response.put("message", "Продукт успешно создан");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
