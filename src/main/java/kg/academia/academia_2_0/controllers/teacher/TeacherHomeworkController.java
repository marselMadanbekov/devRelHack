package kg.academia.academia_2_0.controllers.teacher;

import kg.academia.academia_2_0.model.creations.ExerciseCreate;
import kg.academia.academia_2_0.model.creations.HomeWorkCreate;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.model.entities.users.Pupil;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.homework.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/teacher/homeworks")
public class TeacherHomeworkController {

    private final GroupStorage groupStorage;
    private final HomeWorkService homeWorkService;

    @Autowired
    public TeacherHomeworkController(GroupStorage groupStorage, HomeWorkService homeWorkService) {
        this.groupStorage = groupStorage;
        this.homeWorkService = homeWorkService;
    }

    @GetMapping("/create-homework")
    public String createHomework(@RequestParam Long groupId,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 Model model) {
        Group group = groupStorage.getGroupById(groupId);

        if(!group.getSubject().isMentalArithmetic())
            return "teacher/create-classic-homework";

        List<MentalHomeWork> createdHomeworks = homeWorkService.findCreatedHomeworksByGroupToday(group);
        List<Pupil> listToCreateHomework = homeWorkService.listOfPupilsToCreateHomework(createdHomeworks, group.getPupils());
        Page<MentalHomeWork> pastHomeworks = homeWorkService.getHomeworksPageByGroupAndPage(group,page);
        model.addAttribute("group", group);
        model.addAttribute("createdHomeworks", createdHomeworks);
        model.addAttribute("pupilsToCreateHomework", listToCreateHomework);
        model.addAttribute("pastHomeworks", pastHomeworks);
        return "teacher/create-homework-general-page";
    }

    @GetMapping("/create-mental-homework")
    public String createMentalHomework(@RequestParam Long groupId,
                                       @RequestParam Long pupilId,
                                       @RequestParam Date deadLine) {
        MentalHomeWork created = homeWorkService.createHomeWork(HomeWorkCreate.builder()
                .deadLine(deadLine)
                .groupId(groupId)
                .pupilId(pupilId)
                .build());
        return "redirect:/teacher/homeworks/edit-mental-homework?homeworkId=" + created.getId();
    }

    @GetMapping("/edit-mental-homework")
    public String editMentalHomework(@RequestParam Long homeworkId,
                                     Model model){
        MentalHomeWork homework = homeWorkService.findHomeworkById(homeworkId);
        model.addAttribute("homework", homework);
        return "teacher/create-mental-homework";
    }


    @PostMapping("/add-mental-exercise")
    public ResponseEntity<Map<String,String>> addExerciseToHomework(ExerciseCreate exerciseCreate){
        Map<String,String> response = new HashMap<>();
        try{
            homeWorkService.addExerciseToHomework(exerciseCreate);
            response.put("message", "Упражнение успешно добавлено!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/delete-exercise")
    public ResponseEntity<Map<String,String>> deleteExercise(@RequestParam Long exerciseId){
        Map<String,String> response = new HashMap<>();
        try{
            homeWorkService.deleteExercise(exerciseId);
            response.put("message", "Упражнение успешно удалено");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/delete-homework")
    public ResponseEntity<Map<String,String>> deleteHomework(@RequestParam Long homeworkId){
        Map<String,String> response = new HashMap<>();
        try{
            homeWorkService.deleteHomework(homeworkId);
            response.put("message", "Домашнее задание успешно удалено");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
