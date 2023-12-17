package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.model.entities.Review;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.utilities.ChartTuple;
import kg.academia.academia_2_0.services.comment.ReviewStorage;
import kg.academia.academia_2_0.services.event.EventService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    private final UserService userService;
    private final ContextService contextService;
    private final ReviewStorage reviewStorage;

    @Autowired
    public EventController(EventService eventService, UserService userService, ContextService contextService, ReviewStorage reviewStorage) {
        this.eventService = eventService;
        this.userService = userService;
        this.contextService = contextService;
        this.reviewStorage = reviewStorage;
    }

    @GetMapping()
    public String events(@RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Page<Event> events = eventService.eventsByPage(page);
        Employee employee = contextService.getCurrentEmployee();
        model.addAttribute("currentUser", employee);
        model.addAttribute("events", events);
        return "main";
    }

    @GetMapping("/event-details")
    public String eventDetails(@RequestParam Long eventId,
                               Model model){
        Event event = eventService.findEventById(eventId);
        List<Review> reviews = reviewStorage.findByEvent(event);
        model.addAttribute("event", event);
        model.addAttribute("reviews", reviews);
        return "event-details";
    }
    @GetMapping("/create-event")
    public String createEvent(Model model){
        List<String> skills = userService.findUniqueSkills();
        model.addAttribute("skills", skills);
        return "create-event";
    }

    @PostMapping("/change-attendance")
    public ResponseEntity<Map<String, String>> changeAttendance(@RequestParam Long eventId,
                                                                @RequestParam Long userId) {
        Map<String, String> response = new HashMap<>();
        try{
            eventService.changeAttendanceStatusOfUser(eventId, userId);
            response.put("message", "Успешно изменен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
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


    @PostMapping("/create-review")
    public ResponseEntity<Map<String, String>> createReview(@RequestParam Long eventId,
                                                            @RequestParam String message,
                                                            @RequestParam Integer grade) {
        Map<String, String> response = new HashMap<>();
        try {
            eventService.createComment(eventId,message,grade);
            response.put("message", "Комментарий успешно создан");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/events-analytics")
    public String eventAnalytics(){
        return "events-analytics";
    }

    @PostMapping("/top-events")
    public ResponseEntity<List<ChartTuple>> topEvents(){
        return ResponseEntity.ok(eventService.findTopEventsToChart());
    }
}
