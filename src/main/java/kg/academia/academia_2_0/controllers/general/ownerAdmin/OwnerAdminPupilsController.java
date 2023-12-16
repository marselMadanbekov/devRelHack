package kg.academia.academia_2_0.controllers.general.ownerAdmin;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/owner-admin/pupils")
public class OwnerAdminPupilsController {
    private final UserService userService;

    @Autowired
    public OwnerAdminPupilsController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create-pupil")
    public ResponseEntity<Map<String,String>> createPupilReq(@ModelAttribute EmployeeCreate employeeCreate){
        Map<String,String> response = new HashMap<>();
        try{
            userService.createPupil(employeeCreate);
            response.put("message", "Ученик успешно создан");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
