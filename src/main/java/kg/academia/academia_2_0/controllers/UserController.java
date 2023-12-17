package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import kg.academia.academia_2_0.services.user.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String users(@RequestParam(defaultValue = "0") Integer page,
                        @RequestParam(defaultValue = "-1") Integer level,
                        @RequestParam(defaultValue = "0") String skill,
                        Model model) {
        Page<Employee> employees = userService.getEmployeesByPageAndFilters(page, level, skill);
        List<String> skills = userService.findUniqueSkills();
        model.addAttribute("skills", skills);
        model.addAttribute("employees", employees);
        return "users";
    }



    @GetMapping("/user-details")
    public String userDetails(@RequestParam Long userId,
                              Model model) {
        Employee employee = userService.findEmployeeById(userId);
        model.addAttribute("employee", employee);
        return "user-details";
    }

    @GetMapping("/create-user")
    public String createUser() {
        return "user-create";
    }

    @PostMapping("/create-user")
    public ResponseEntity<Map<String, String>> createUser(EmployeeCreate employeeCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println(employeeCreate.toString());
            userService.createEmployee(employeeCreate);
            response.put("message", "Пользователь успешно создан!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/add-skill")
    public ResponseEntity<Map<String, String>> addSkills(@RequestParam Long employeeId,
                                                         @RequestParam String skillName) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.addSkill(employeeId, skillName);
            response.put("message", "Навык успешно добавлен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/add-social-media")
    public ResponseEntity<Map<String, String>> addSocMedia(@RequestParam Long userId,
                                                           @RequestParam String socMedia,
                                                           @RequestParam String username) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.addSocMediaToEmployee(userId, socMedia, username);
            response.put("message", "Навык успешно добавлен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update-user-data")
    public ResponseEntity<Map<String, String>> updateUserData(@ModelAttribute UserdataUpdate update) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.updateUserdata(update);
            response.put("message", "Данные успешно изменены");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/mail-distribution")
    public String mailDistribution(@RequestParam Long userId, Model model) {
        Employee employee = userService.findEmployeeById(userId);
        model.addAttribute("employee", employee);
        return "mail-distribution";
    }

    @PostMapping("/send-distribution")
    public ResponseEntity<Map<String, String>> sendDistribution(@RequestParam Long userId,
                                                                @RequestParam String message,
                                                                @RequestParam List<String> targetDistributions) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.sendDistributionsByUser(userId, message, targetDistributions);
            response.put("message", "Социальная сеть успешно удалена!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/delete-social-media")
    public ResponseEntity<Map<String, String>> deleteSocMedia(@RequestParam Long userId,
                                                              @RequestParam String socMedia) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.deleteSocMedia(userId, socMedia);
            response.put("message", "Социальная сеть успешно удалена!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete-skill")
    public ResponseEntity<Map<String, String>> deleteSkills(@RequestParam Long employeeId,
                                                            @RequestParam String skill) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.deleteSkill(employeeId, skill);
            response.put("message", "Навык успешно удален!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
