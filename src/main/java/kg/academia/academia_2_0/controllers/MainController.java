package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainController {
    private final UserStorage userStorage;
    private final UserService userService;
    @Autowired
    public MainController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public String main(Principal principal){
        return "admin/main";
    }

    @GetMapping("/profile")
    public String profile(Principal principal){
        return "admin/profile";
    }

    @PostMapping("/profile/update-user-data")
    public ResponseEntity<Map<String,String>> updateProfile(@ModelAttribute UserdataUpdate update){
        Map<String,String> response = new HashMap<>();
        try{
            userService.updateUserdata(update);
            response.put("message", "Данные успешно обновлены!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
