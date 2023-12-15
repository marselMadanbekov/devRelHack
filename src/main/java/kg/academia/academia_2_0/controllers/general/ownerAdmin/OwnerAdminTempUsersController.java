package kg.academia.academia_2_0.controllers.general.ownerAdmin;

import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/owner-admin/temp-users")
public class OwnerAdminTempUsersController {
    private final UserService userService;

    @Autowired
    public OwnerAdminTempUsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reject")
    public ResponseEntity<Map<String,String>> rejectUser(@RequestParam Long tempUserId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.rejectRequestOfTempUser(tempUserId);
            response.put("message", "Запрос отклонён, пользователь будет удален из списка!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<Map<String,String>> applyUser(@RequestParam Long tempUserId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.applyRequestOfTempUser(tempUserId);
            response.put("message", "Запрос успешно принять, пользователь теперь является учеником филиала!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
