package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.enums.Role;
import kg.academia.academia_2_0.services.mark.MarkService;
import kg.academia.academia_2_0.services.security.ContextService;
import kg.academia.academia_2_0.services.trainer.TrainerService;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    private final ContextService contextService;
    private final TrainerService trainerService;
    private final MarkService markService;
    private final UserService userService;

    @Autowired
    public TrainerController(ContextService contextService, TrainerService trainerService, MarkService markService, UserService userService) {
        this.contextService = contextService;
        this.trainerService = trainerService;
        this.markService = markService;
        this.userService = userService;
    }

    @GetMapping("")
    public String trainer(Model model) {
        Employee employee = contextService.getCurrentUsersData();
        String targetPage = "";
        switch (employee.getRole()) {
            case ROLE_SUPER_ADMIN, ROLE_BRANCH_OWNER, ROLE_ADMIN, ROLE_PUPIL -> {
                targetPage = "trainer/simple-trainer";
            }
            case ROLE_TEACHER -> {
                Teacher teacher = contextService.getCurrentTeacher();
                List<Pupil> pupils = userService.findPupilsByBranch(teacher.getBranch());
                model.addAttribute("pupils", pupils);
                targetPage = "trainer/teachers-trainer-1";
            }
            case ROlE_TEMP_USER -> {
            }
        }
        return targetPage;
    }
    @GetMapping("/trainer-2")
    public String trainer2(Model model) {
        Teacher teacher = contextService.getCurrentTeacher();
        List<Pupil> pupils = userService.findPupilsByBranch(teacher.getBranch());
        model.addAttribute("pupils", pupils);
        return "trainer/teachers-trainer-2";
    }
    @GetMapping("/trainer-3")
    public String trainer3(Model model) {
        Teacher teacher = contextService.getCurrentTeacher();
        List<Pupil> pupils = userService.findPupilsByBranch(teacher.getBranch());
        model.addAttribute("pupils", pupils);
        return "trainer/teachers-trainer-3";
    }
    @GetMapping("/trainer-4")
    public String trainer4(Model model) {
        Teacher teacher = contextService.getCurrentTeacher();
        List<Pupil> pupils = userService.findPupilsByBranch(teacher.getBranch());
        model.addAttribute("pupils", pupils);
        return "trainer/teachers-trainer-4";
    }

    @PostMapping("/get-trainer-task")
    public ResponseEntity<Map<String, Object>> getTrainerTask(@RequestParam Integer digits,
                                                              @RequestParam Integer count,
                                                              @RequestParam String topic) {
        Map<String, Object> response = new HashMap<>();
        try {
            MentalTask mentalTask = trainerService.getTasksByTaskName(topic, digits, count, 1).get(0);
            response.put("task", mentalTask);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/get-multiple-trainer-tasks")
    public ResponseEntity<Map<String, MentalTask>> getTrainerTasks(@RequestParam Integer digits,
                                                               @RequestParam Integer count,
                                                               @RequestParam String topic1,
                                                               @RequestParam(required = false) String topic2,
                                                               @RequestParam(required = false) String topic3,
                                                               @RequestParam(required = false) String topic4) {
        try {
            Map<String,MentalTask> tasks = trainerService.getMultipleTasksByTopicsAndGeneralInfo(topic1,topic2,topic3,topic4, digits, count, 1);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/submit-result")
    public ResponseEntity<Map<String, String>> submitResult(@RequestParam String topic,
                                                            @RequestParam Boolean isCorrect) {
        Map<String, String> response = new HashMap<>();
        try {
            if (!contextService.getCurrentUsersData().getRole().equals(Role.ROLE_PUPIL)) {
                response.put("message", "Результат будет сохранен только для учеников!");
            } else {
                markService.createIndependentWorkTrainerMark(topic, isCorrect);
                response.put("message", "Результат успешно сохранен!");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/submit-result-of-pupil")
    public ResponseEntity<Map<String, String>> submitResult(@RequestParam String topic,
                                                            @RequestParam Long pupilId,
                                                            @RequestParam Boolean isCorrect) {
        Map<String, String> response = new HashMap<>();
        try {
            markService.createIndependentWorkTrainerMarkByPupil(topic, isCorrect, pupilId);
            response.put("message", "Результат успешно сохранен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/submit-multiple-results")
    public ResponseEntity<Map<String, String>> submitResult(@RequestBody MultipleTrainerResults results) {
        Map<String, String> response = new HashMap<>();
        try {
            markService.createMarksByMultipleTrainerResults(results);
            response.put("message", "Результаты успешно сохранены!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
