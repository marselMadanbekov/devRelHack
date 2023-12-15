package kg.academia.academia_2_0.controllers.general.superAdminOwner;

import kg.academia.academia_2_0.model.creations.UserCreate;
import kg.academia.academia_2_0.model.utilities.ChartTuple;
import kg.academia.academia_2_0.services.branch.BranchAnalyticsService;
import kg.academia.academia_2_0.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/super-admin-owner")
public class SuperAdminOwnerController {
    private final UserService userService;
    private final BranchAnalyticsService branchAnalyticsService;

    @Autowired
    public SuperAdminOwnerController(UserService userService, BranchAnalyticsService branchAnalyticsService) {
        this.userService = userService;
        this.branchAnalyticsService = branchAnalyticsService;
    }

    @PostMapping("/admins/create-admin")
    public ResponseEntity<Map<String, String>> createOwnerRequest(@ModelAttribute UserCreate userCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.createAdmin(userCreate);
            System.out.println(userCreate.toString());
            response.put("message", "Администратор успешно создан");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/branches/branch-analytics/payments")
    public ResponseEntity<List<ChartTuple>> paymentAnalytics(@RequestParam Long branchId,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer monthShift) {
        try {
            List<ChartTuple> data = branchAnalyticsService.getMonthlyPaymentsSumByBranch(branchId, monthShift);
            return ResponseEntity.ok(data);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/branches/branch-analytics/new-pupils")
    public ResponseEntity<List<ChartTuple>> newPupilsAnalytics(@RequestParam Long branchId,
                                                               @RequestParam(required = false, defaultValue = "0") Integer monthShift){
        try{
            List<ChartTuple> data = branchAnalyticsService.getMonthlyPupilsCountByBranch(branchId,monthShift);
            return ResponseEntity.ok(data);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/branches/branch-analytics/pupils-subjects")
    public ResponseEntity<List<ChartTuple>> pupilsSubjects(@RequestParam Long branchId){
        try{
            List<ChartTuple> data = branchAnalyticsService.getPupilsCountBySubjectsAndBranch(branchId);
            return ResponseEntity.ok(data);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }
}
