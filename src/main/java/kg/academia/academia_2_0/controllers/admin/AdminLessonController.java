package kg.academia.academia_2_0.controllers.admin;

import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Lesson;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.lesson.LessonService;
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
    private final LessonService lessonService;

    @Autowired
    public AdminLessonController(GroupStorage groupStorage, LessonService lessonService) {
        this.groupStorage = groupStorage;
        this.lessonService = lessonService;
    }

    @GetMapping("/lessons-history")
    public String lessonsHistory(@RequestParam Long groupId,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Group group = groupStorage.getGroupById(groupId);
        Page<Lesson> lessons = lessonService.lessonsByGroupIdAndPage(groupId, page);
        model.addAttribute("group", group);
        model.addAttribute("lessons", lessons);
        return "admin/lessons-history";
    }

    @GetMapping("/lesson-details")
    public String lessonDetails(@RequestParam Long lessonId,
                                Model model){
        Lesson lesson = lessonService.findLessonById(lessonId);
        model.addAttribute("lesson", lesson);
        return "admin/lesson-details";
    }
}
