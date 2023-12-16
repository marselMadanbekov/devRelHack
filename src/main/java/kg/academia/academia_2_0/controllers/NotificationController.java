package kg.academia.academia_2_0.controllers;

import kg.academia.academia_2_0.model.entities.Notification;
import kg.academia.academia_2_0.model.entities.users.Employee;
import kg.academia.academia_2_0.services.notification.NotificationService;
import kg.academia.academia_2_0.services.notification.NotificationStorage;
import kg.academia.academia_2_0.services.security.ContextService;
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
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationStorage notificationStorage;
    private final ContextService contextService;
    @Autowired
    public NotificationController(NotificationService notificationService, NotificationStorage notificationStorage, ContextService contextService) {
        this.notificationService = notificationService;
        this.notificationStorage = notificationStorage;
        this.contextService = contextService;
    }


    @GetMapping
    public String notifications(Model model){
        Employee employee = contextService.getCurrentEmployee();
        List<Notification> notifications = notificationStorage.getNonViewedNotificationsByUserData(employee);
        model.addAttribute("notifications", notifications);
        return "admin/notifications";
    }

    @GetMapping("/having-non-read")
    public ResponseEntity<Map<String,Object>> hadNotifications(){
        Map<String, Object> response = new HashMap<>();
        try{
            boolean isHave = notificationService.isCurrentUserHaveNonViewedNotifications();
            response.put("isHave", isHave);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/viewed")
    public ResponseEntity<Map<String,String>> setViewed(@RequestParam Long notificationId){
        Map<String,String> response = new HashMap<>();

        try{
            notificationService.notificationViewed(notificationId);
            response.put("message", "Помечено как прочитано!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
