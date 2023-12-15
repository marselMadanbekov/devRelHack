package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.model.creations.LessonCreate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.model.updations.AttendanceUpdate;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.lesson.LessonService;
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
    private final LessonService lessonService;

    @Autowired
    public TeacherLessonController(GroupStorage groupStorage, LessonService lessonService) {
        this.groupStorage = groupStorage;
        this.lessonService = lessonService;
    }

    @GetMapping("/group-lessons")
    public String attendance(@RequestParam Long groupId,
                             Model model) {
        Group group = groupStorage.getGroupById(groupId);
        List<Lesson> lessons = lessonService.lastLessonsByGroupId(groupId);
        model.addAttribute("group", group);
        model.addAttribute("lessons", lessons);
        return "teacher/lessons";
    }

    @GetMapping("/lessons-history")
    public String lessonsHistory(@RequestParam Long groupId,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Group group = groupStorage.getGroupById(groupId);
        Page<Lesson> lessons = lessonService.lessonsByGroupIdAndPage(groupId, page);
        model.addAttribute("group", group);
        model.addAttribute("lessons", lessons);
        return "teacher/lessons-history";
    }
    @GetMapping("/lesson-details")
    public String lessonDetails(@RequestParam Long lessonId,
                                Model model){
        Lesson lesson = lessonService.findLessonById(lessonId);
        model.addAttribute("lesson", lesson);
        return "teacher/lesson-details";
    }

    @GetMapping("/last-lessons")
    public String lastLesson(@RequestParam Long lessonId,
                             Model model) {
        Lesson lesson = lessonService.findLessonById(lessonId);
        model.addAttribute("lesson", lesson);
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
    public ResponseEntity<Map<String, String>> submitAttendance(@RequestBody LessonCreate lessonCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println(lessonCreate.getDate());
            System.out.println(lessonCreate.getGroupId());
            lessonService.createLesson(lessonCreate);
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
            lessonService.editLesson(lessonCreate);
            response.put("message", "Урок успешно сохранен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
