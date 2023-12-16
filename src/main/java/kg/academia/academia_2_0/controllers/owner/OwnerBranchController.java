package kg.academia.academia_2_0.controllers.owner;


import kg.academia.academia_2_0.services.branch.BranchService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/owner/branches")
public class OwnerBranchController {
    private final BranchService branchService;
    private final UserStorage userStorage;

    @Autowired
    public OwnerBranchController(BranchService branchService, UserStorage userStorage) {
        this.branchService = branchService;
        this.userStorage = userStorage;
    }

    @GetMapping
    public String branches(Model model){
        List<Branch> branches = branchService.findBranchesOfCurrentOwner();
        model.addAttribute("branches", branches);
        return "owner/main";
    }

    @GetMapping("/branch-details")
    public String branchDetails(@RequestParam Long branchId,
                                Model model) {
        Branch branch = branchService.getBranchById(branchId);
        Owner owner = null;
        Admin admin = null;
        try {
            owner = userStorage.getOwnerByBranch(branch);
            admin = userStorage.getAdminByBranch(branch);
        }catch (Exception ignore){}

        model.addAttribute("owner", owner);
        model.addAttribute("admin", admin);
        model.addAttribute("branch", branch);
        return "owner/branch-details";
    }

    @GetMapping("/branch-analytics")
    public String branchAnalytics(@RequestParam Long branchId,
                                  Model model){
        Branch branch = branchService.getBranchById(branchId);
        model.addAttribute("branch", branch);
        return "owner/branch-analytics";
    }
}
