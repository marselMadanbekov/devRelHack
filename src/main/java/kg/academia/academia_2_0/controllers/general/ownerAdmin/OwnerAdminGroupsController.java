package kg.academia.academia_2_0.controllers.general.ownerAdmin;

import kg.academia.academia_2_0.model.creations.GroupCreate;
import kg.academia.academia_2_0.services.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/owner-admin/groups")
public class OwnerAdminGroupsController {
    private final GroupService groupService;

    @Autowired
    public OwnerAdminGroupsController(GroupService groupService) {
        this.groupService = groupService;
    }



    @PostMapping("/create-group")
    public ResponseEntity<Map<String,String>> createGroupReq(@ModelAttribute GroupCreate groupCreate){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.createGroup(groupCreate);
            response.put("message", "Группа успешно создана");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/remove-pupil")
    public ResponseEntity<Map<String,String>> removePupil(@RequestParam Long groupId,
                                                          @RequestParam Long pupilId){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.removePupil(groupId,pupilId);
            response.put("message", "Ученик успешно удален из группы");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update-group")
    public ResponseEntity<Map<String,String>> updateGroup(@RequestParam Long groupId,
                                                          @RequestParam(required = false) String name,
                                                          @RequestParam(defaultValue = "0") Long subjectId,
                                                          @RequestParam(defaultValue = "0") Long teacherId){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.updateGroup(groupId, name, subjectId, teacherId);
            response.put("message", "Данные группы успешно изменены");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/add-lesson")
    public ResponseEntity<Map<String,String>> addLesson(@RequestParam Long groupId,
                                                        @RequestParam Integer dayOfWeek,
                                                        @RequestParam String clock){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.addLessonToTimetable(groupId, dayOfWeek,clock);
            response.put("message", "Урок успешно добавлен");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/delete-lesson-day")
    public ResponseEntity<Map<String,String>> deleteLessonDay(@RequestParam Long groupId,
                                                              @RequestParam Integer dayOfWeek){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.deleteLessonDay(groupId, dayOfWeek);
            response.put("message", "День успешно удален из расписания");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/add-pupils")
    public ResponseEntity<Map<String,String>> addPupilsReq(@RequestParam Long groupId,
                                                           @RequestParam List<Long> pupils){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.addPupilsToGroup(groupId, pupils);
            response.put("message", "Ученики успешно добавлены");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/activate-deactivate")
    public ResponseEntity<Map<String,String>> activateDeactivate(@RequestParam Long groupId){
        Map<String,String> response = new HashMap<>();
        try{
            groupService.changeStatus(groupId);
            response.put("message", "Статус группы успешно изменен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
