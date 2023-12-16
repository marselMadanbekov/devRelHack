package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/teacher/pupils")
public class TeacherPupilsController {
    private final UserStorage userStorage;
    private final SubjectStorage subjectStorage;
    private final ContextService contextService;
    private final GroupStorage groupStorage;

    @Autowired
    public TeacherPupilsController(UserStorage userStorage, SubjectStorage subjectStorage, ContextService contextService, GroupStorage groupStorage) {
        this.userStorage = userStorage;
        this.subjectStorage = subjectStorage;
        this.contextService = contextService;
        this.groupStorage = groupStorage;
    }

    @GetMapping
    public String pupils(Model model){
        Teacher teacher = contextService.getCurrentTeacher();
        List<Pupil> pupils = userStorage.findAllPupilsByBranch(teacher.getBranch());
        model.addAttribute("pupils", pupils);
        return "teacher/pupils";
    }

    @GetMapping("/pupil-details")
    public String pupilDetails(@RequestParam Long pupilId,
                               Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        List<Group> groups = groupStorage.findAllByPupil(pupil);
        model.addAttribute("pupil", pupil);
        model.addAttribute("groups", groups);
        return "teacher/pupil-details";
    }

    @GetMapping("/pupil-statistics")
    public String pupilStatistics(@RequestParam Long pupilId,
                                  @RequestParam Long subjectId,
                                  Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        Subject subject = subjectStorage.getSubjectById(subjectId);
        model.addAttribute("pupil", pupil);
        model.addAttribute("subject",subject);
        return "teacher/pupil-statistics";
    }
}
