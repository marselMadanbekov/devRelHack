package kg.academia.academia_2_0.controllers.pupil;

import kg.academia.academia_2_0.model.entities.homeWork.MentalExercise;
import kg.academia.academia_2_0.model.entities.homeWork.MentalHomeWork;
import kg.academia.academia_2_0.services.homework.HomeWorkService;
import kg.academia.academia_2_0.services.trainer.TrainerService;
import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pupil/homeworks")
public class PupilHomeworkController {

    private final HomeWorkService homeWorkService;
    private final TrainerService trainerService;

    @Autowired
    public PupilHomeworkController(HomeWorkService homeWorkService, TrainerService trainerService) {
        this.homeWorkService = homeWorkService;
        this.trainerService = trainerService;
    }

    @GetMapping()
    public String homeworks(Model model) {
        List<MentalHomeWork> homeworks = homeWorkService.homeworksByCurrentPupil();
        model.addAttribute("homeworks", homeworks);
        return "pupil/homeworks";
    }

    @GetMapping("/homework-details")
    public String homeworkDetails(@RequestParam Long homeworkId,
                                  Model model) {
        MentalHomeWork mentalHomeWork = homeWorkService.findHomeworkById(homeworkId);
        model.addAttribute("homework", mentalHomeWork);
        return "pupil/homework-details";
    }

    @GetMapping("/do-exercise")
    public String doExercise(@RequestParam Long exerciseId,
                             Model model) {
        MentalExercise exercise = homeWorkService.findExerciseById(exerciseId);
        if (exercise.getType().equals(ExerciseType.TEST)) {
            List<MentalTask> mentalTasks = trainerService.getTasksByExercise(exercise);
            model.addAttribute("tasks", mentalTasks);
            model.addAttribute("exercise", exercise);
            return "pupil/homework-test-page";
        } else {
            model.addAttribute("exercise", exercise);
            return "pupil/homework-trainer-page";
        }
    }

    @GetMapping("/remake-exercise")
    public String remakeExercise(@RequestParam Long exerciseId) {
        MentalExercise exercise = homeWorkService.findExerciseById(exerciseId);
        homeWorkService.clearExerciseProgress(exercise);
        return "redirect:/pupil/homeworks/do-exercise?exerciseId=" + exercise.getId();
    }

    @PostMapping("/submit-test")
    public ResponseEntity<Map<String, String>> submitTest(@RequestParam Integer totalQuestions,
                                                          @RequestParam Integer correctAnswers,
                                                          @RequestParam Long exerciseId) {
        Map<String, String> response = new HashMap<>();
        try {
            homeWorkService.submitTestResults(totalQuestions, correctAnswers, exerciseId);
            response.put("message", "Прогресс успешно сохранен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/submit-trainer")
    public ResponseEntity<Map<String, String>> submitTrainer(@RequestParam Long exerciseId,
                                                             @RequestParam Boolean isCorrect) {
        Map<String, String> response = new HashMap<>();
        try {
            MentalExercise exercise = homeWorkService.submitTrainerResult(exerciseId, isCorrect);
            response.put("correct", exercise.getCorrect() + "");
            response.put("solved", exercise.getSolved() + "");
            response.put("total", exercise.getQuestionCount() + "");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/get-trainer-task")
    public ResponseEntity<Map<String, Object>> getTrainerTask(@RequestParam Integer digits,
                                                              @RequestParam Integer count,
                                                              @RequestParam Integer taskOrdinal) {
        Map<String, Object> response = new HashMap<>();
        try {
            MentalTask mentalTask = trainerService.getTaskByDigitsAndCount(TrainerTask.values()[taskOrdinal].getGenerator(), digits, count);
            response.put("task", mentalTask);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
