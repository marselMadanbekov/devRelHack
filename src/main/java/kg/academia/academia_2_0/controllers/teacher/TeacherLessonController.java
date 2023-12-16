package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.model.creations.EventCreate;
import kg.academia.academia_2_0.model.entities.Event;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/lessons")
public class TeacherLessonController {

    private final GroupStorage groupStorage;
    private final EventService eventService;

    @Autowired
    public TeacherLessonController(GroupStorage groupStorage, EventService eventService) {
        this.groupStorage = groupStorage;
        this.eventService = eventService;
    }

    @GetMapping("/group-lessons")
    public String attendance(@RequestParam Long groupId,
                             Model model) {
        Group group = groupStorage.getGroupById(groupId);
        List<Event> events = eventService.lastLessonsByGroupId(groupId);
        model.addAttribute("group", group);
        model.addAttribute("lessons", events);
        return "teacher/lessons";
    }

    @GetMapping("/lessons-history")
    public String lessonsHistory(@RequestParam Long groupId,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Group group = groupStorage.getGroupById(groupId);
        Page<Event> lessons = eventService.lessonsByGroupIdAndPage(groupId, page);
        model.addAttribute("group", group);
        model.addAttribute("lessons", lessons);
        return "teacher/lessons-history";
    }
    @GetMapping("/lesson-details")
    public String lessonDetails(@RequestParam Long lessonId,
                                Model model){
        Event event = eventService.findLessonById(lessonId);
        model.addAttribute("lesson", event);
        return "teacher/lesson-details";
    }

    @GetMapping("/last-lessons")
    public String lastLesson(@RequestParam Long lessonId,
                             Model model) {
        Event event = eventService.findLessonById(lessonId);
        model.addAttribute("lesson", event);
        return "teacher/edit-lesson";
    }

    @GetMapping("/create-lesson")
    public String createLesson(@RequestParam Long groupId,
                               Model model) {
        Group group = groupStorage.getGroupById(groupId);
        model.addAttribute("group", group);
        return "teacher/create-lesson";
    }

    @PostMapping("/submit-attendance")
    public ResponseEntity<Map<String, String>> submitAttendance(@RequestBody EventCreate eventCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println(eventCreate.getDate());
            System.out.println(eventCreate.getGroupId());
            eventService.createEvent(eventCreate);
            response.put("message", "Урок успешно создан!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/edit-attendance")
    public ResponseEntity<Map<String, String>> editAttendance(@RequestBody AttendanceUpdate lessonCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            eventService.editLesson(lessonCreate);
            response.put("message", "Урок успешно сохранен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
