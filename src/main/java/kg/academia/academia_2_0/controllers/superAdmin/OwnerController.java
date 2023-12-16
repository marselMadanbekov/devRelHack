package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.model.creations.EmployeeCreate;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/super-admin/owners")
public class OwnerController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public OwnerController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping("/create-owner")
    public String createOwner(@RequestParam Long branchId,
                              Model model){
        List<Owner> owners = userStorage.findAllOwners();
        model.addAttribute("owners", owners);
        model.addAttribute("branchId", branchId);
        return "superadmin/create-owner";
    }

    @PostMapping("/create-owner")
    public ResponseEntity<Map<String,String>> createOwnerRequest(@ModelAttribute EmployeeCreate employeeCreate){
        Map<String,String> response = new HashMap<>();
        try{
            userService.createOwner(employeeCreate);
            System.out.println(employeeCreate.toString());
            response.put("message", "Владелец успешно создан");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
