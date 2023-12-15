package kg.academia.academia_2_0.controllers.general.ownerAdmin;

import kg.academia.academia_2_0.model.updations.UserdataUpdate;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/owner-admin/users")
public class OwnerAdminUserDataController {
    private final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public OwnerAdminUserDataController(UserService userService, UserStorage userStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @PostMapping("/activate-deactivate")
    public ResponseEntity<Map<String,String>> activateDeactivate(@RequestParam Long userdataId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.changeStatusOfUser(userdataId);
            response.put("message", "Статус успешно изменен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/update-user-data")
    public ResponseEntity<Map<String,String>> updateUserData(@ModelAttribute UserdataUpdate update){
        Map<String,String> response = new HashMap<>();
        try{
            userService.updateUserdata(update);
            response.put("message", "Данные успешно изменены");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/delete-pupil")
    public ResponseEntity<Map<String,String>> deletePupil(@RequestParam Long pupilId){
        Map<String,String> response = new HashMap<>();
        try{
            userStorage.deletePupilById(pupilId);
            response.put("message", "Ученик успешно удален!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/delete-teacher")
    public ResponseEntity<Map<String,String>> deleteTeacher(@RequestParam Long teacherId){
        Map<String,String> response = new HashMap<>();
        try{
            userStorage.deleteTeacherById(teacherId);
            response.put("message", "Учитель успешно удален!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
