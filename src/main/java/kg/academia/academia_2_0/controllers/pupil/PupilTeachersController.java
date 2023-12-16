package kg.academia.academia_2_0.controllers.pupil;

import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/pupil/teachers")
public class PupilTeachersController {

    private final ContextService contextService;
    private final UserStorage userStorage;
    private final GroupStorage groupStorage;

    @Autowired
    public PupilTeachersController(ContextService contextService, UserStorage userStorage, GroupStorage groupStorage) {
        this.contextService = contextService;
        this.userStorage = userStorage;
        this.groupStorage = groupStorage;
    }

    @GetMapping
    public String teachers(Model model){
        Pupil pupil = contextService.getCurrentPupil();
        List<Teacher> teachers = userStorage.findAllTeachersByBranch(pupil.getBranch());
        model.addAttribute("teachers", teachers);
        return "pupil/teachers";
    }

    @GetMapping("/teacher-details")
    public String teacherDetails(@RequestParam Long teacherId,
                                 Model model){
        Pupil pupil = contextService.getCurrentPupil();
        Teacher teacher = userStorage.getTeacher(teacherId);
        List<Group> groups = groupStorage.findAllByPupil(pupil);
        model.addAttribute("teacher", teacher);
        model.addAttribute("groups", groups);
        return "pupil/teacher-details";
    }
}
