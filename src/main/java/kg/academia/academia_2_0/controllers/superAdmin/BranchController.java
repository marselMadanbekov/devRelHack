package kg.academia.academia_2_0.controllers.superAdmin;

import kg.academia.academia_2_0.model.creations.BranchCreate;
import kg.academia.academia_2_0.model.entities.Branch;
import kg.academia.academia_2_0.model.entities.Group;
import kg.academia.academia_2_0.model.entities.Subject;
import kg.academia.academia_2_0.model.entities.users.Admin;
import kg.academia.academia_2_0.model.entities.users.Owner;
import kg.academia.academia_2_0.services.branch.BranchService;
import kg.academia.academia_2_0.services.branch.BranchStorage;
import kg.academia.academia_2_0.services.group.GroupStorage;
import kg.academia.academia_2_0.services.subject.SubjectStorage;
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
@RequestMapping("/super-admin/branches")
public class BranchController {

    private final BranchStorage branchStorage;
    private final UserStorage userStorage;
    private final BranchService branchService;
    private final GroupStorage groupStorage;
    private final SubjectStorage subjectStorage;

    @Autowired
    public BranchController(BranchStorage branchStorage, UserStorage userStorage, BranchService branchService, GroupStorage groupStorage, SubjectStorage subjectStorage) {
        this.branchStorage = branchStorage;
        this.userStorage = userStorage;
        this.branchService = branchService;
        this.groupStorage = groupStorage;
        this.subjectStorage = subjectStorage;
    }

    @GetMapping
    public String main(Model model) {
        List<Branch> branches = branchStorage.findAll();
        List<Owner> owners = userStorage.findAllOwners();

        model.addAttribute("owners", owners);
        model.addAttribute("branches", branches);
        return "superadmin/main";
    }

    @GetMapping("/branch-details")
    public String branchDetails(@RequestParam Long branchId,
                                Model model) {
        Branch branch = branchStorage.getBranchById(branchId);
        List<Group> groups = groupStorage.findAllByBranch(branch);
        List<Subject> subjects = subjectStorage.findAllByBranch(branch);
        Owner owner = null;
        Admin admin = null;
        try {
            owner = userStorage.getOwnerByBranch(branch);
            admin = userStorage.getAdminByBranch(branch);
        }catch (Exception ignore){}

        model.addAttribute("subjects", subjects);
        model.addAttribute("groups", groups);
        model.addAttribute("owner", owner);
        model.addAttribute("admin", admin);
        model.addAttribute("branch", branch);
        return "superadmin/branch-details";
    }

    @GetMapping("/branch-analytics")
    public String branchAnalytics(@RequestParam Long branchId,
                                  Model model){
        Branch branch = branchStorage.getBranchById(branchId);
        model.addAttribute("branch", branch);
        return "superadmin/branch-analytics";
    }
    @GetMapping("/create-branch")
    public String createBranch() {
        return "superadmin/create-branch";
    }

    @PostMapping("/create-branch")
    public ResponseEntity<Map<String, String>> createBranchRequest(@ModelAttribute BranchCreate branchCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            System.out.println(branchCreate.getName());
            System.out.println(branchCreate.getState());
            System.out.println(branchCreate.getTown());
            branchService.createBranch(branchCreate);
            response.put("message", "Филиал успешно создан");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/set-owner")
    public ResponseEntity<Map<String, String>> setOwner(@RequestParam Long branchId,
                                                        @RequestParam Long ownerId) {
        Map<String, String> response = new HashMap<>();
        try {
            branchService.setOwner(branchId, ownerId);
            response.put("message", "Владелец успешно назначен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/activate-deactivate")
    public ResponseEntity<Map<String,String>> activateDeactivate(@RequestParam Long branchId){
        Map<String,String> response = new HashMap<>();
        try{
            branchService.changeStatus(branchId);
            response.put("message", "Статус филиала успешно изменен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
