package kg.academia.academia_2_0.controllers.owner;

import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/owner/payments")
public class OwnerPaymentController {
    private final UserStorage userStorage;

    @Autowired
    public OwnerPaymentController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping("/create-payment")
    public String createPayment(@RequestParam Long pupilId,
                                Model model){
        Pupil pupil = userStorage.getPupil(pupilId);
        model.addAttribute("pupil", pupil);
        return "owner/create-payment";
    }
}
