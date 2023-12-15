package kg.academia.academia_2_0.controllers.general.ownerAdmin;


import kg.academia.academia_2_0.model.creations.UserCreate;
import kg.academia.academia_2_0.services.user.UserService;
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
@RequestMapping("/owner-admin/teachers")
public class OwnerAdminTeachersController {
    private final UserService userService;

    @Autowired
    public OwnerAdminTeachersController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/create-teacher")
    public ResponseEntity<Map<String,String>> createTeacherReq(@ModelAttribute UserCreate userCreate){
        Map<String,String> response = new HashMap<>();
        try{
            userService.createTeacher(userCreate);
            response.put("message", "Учитель успешно создан");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/add-subject-to-teacher")
    public ResponseEntity<Map<String,String>> addSubjectToTeacher(@RequestParam Long teacherId,
                                                                  @RequestParam Long subjectId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.addLessonToTeacher(teacherId, subjectId);
            response.put("message", "Предмет успешно добавлен учителю");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete-subject-for-teacher")
    public ResponseEntity<Map<String,String>> deleteSubjectForTeacher(@RequestParam Long teacherId,
                                                                      @RequestParam Long subjectId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.deleteSubjectForTeacher(teacherId, subjectId);
            response.put("message", "Предмет успешно удален из списка учителя!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
