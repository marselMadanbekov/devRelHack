package kg.academia.academia_2_0.controllers.general.ownerAdmin;

import kg.academia.academia_2_0.services.subject.SubjectService;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
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
@RequestMapping("/owner-admin/subjects")
public class OwnerAdminSubjectsController {
    private final SubjectService subjectService;

    private final SubjectStorage subjectStorage;
    @Autowired
    public OwnerAdminSubjectsController(SubjectService subjectService, SubjectStorage subjectStorage) {
        this.subjectService = subjectService;
        this.subjectStorage = subjectStorage;
    }

    @PostMapping("/create-subject")
    public ResponseEntity<Map<String, String>> createSubjectReq(@ModelAttribute SubjectCreate subjectCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            subjectService.createSubject(subjectCreate);
            response.put("message", "Предмет успешно создан");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/activate-deactivate")
    public ResponseEntity<Map<String,String>> activateDeactivate(@RequestParam Long subjectId){
        Map<String,String> response = new HashMap<>();
        try{
            subjectService.changeStatus(subjectId);
            response.put("message", "Статус успешно изменен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update-subject")
    public ResponseEntity<Map<String,String>> updateSubject(@RequestParam Long subjectId,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) Integer costPerLesson){
        Map<String,String> response = new HashMap<>();
        try{
            subjectService.updateSubject(subjectId, name, costPerLesson);
            response.put("message", "Данные успешно обновлены!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete-subject")
    public ResponseEntity<Map<String,String>> deleteSubject(@RequestParam Long subjectId){
        Map<String,String> response = new HashMap<>();
        try{
            subjectStorage.deleteById(subjectId);
            response.put("message", "Предмет успешно удален!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
