package kg.academia.academia_2_0.controllers.general.ownerAdmin;

import kg.academia.academia_2_0.services.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/owner-admin/payments")
public class OwnerAdminPaymentController {

    private final PaymentService paymentService;

    @Autowired
    public OwnerAdminPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-payment")
    public ResponseEntity<Map<String,String>> createPayment(@RequestParam Long pupilId,
                                                            @RequestParam Integer amount){
        Map<String,String> response = new HashMap<>();
        try{
            paymentService.createPayment(pupilId, amount);
            response.put("message", "Оплата успешно проведена!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
