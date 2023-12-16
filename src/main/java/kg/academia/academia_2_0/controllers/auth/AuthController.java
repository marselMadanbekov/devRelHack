package kg.academia.academia_2_0.controllers.auth;

import kg.academia.academia_2_0.security.JwtTokenProvider;
import kg.academia.academia_2_0.services.user.UserService;
import kg.academia.academia_2_0.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserService userService, UserStorage userStorage) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userStorage = userStorage;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/sign-up")
    public String register(Model model) {
        return "auth/register";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

    @GetMapping("/auth/password-reset")
    public String passwordReset() {
        return "auth/password-reset";
    }

//    @PostMapping("/auth/password-reset")
//    public ResponseEntity<Map<String, String>> passwordReset(@RequestParam String username) {
//        Map<String, String> response = new HashMap<>();
//        try {
//            passwordResetService.passwordResetCreateConfirmationCodeByUsername(username);
//            response.put("username", username);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @GetMapping("/auth/confirmation-code")
//    public String confirmationCodePage(@RequestParam String username,
//                                       Model model) {
//        Employee employee = userStorage.getUserDataByUsername(username);
//        model.addAttribute("username", username);
//        model.addAttribute("email", employee.getEmail());
//        return "auth/confirmation-code-page";
//    }
//
//    @PostMapping("/auth/confirmation-code")
//    public ResponseEntity<Map<String, String>> confirmationCode(@RequestParam String username,
//                                                                @RequestParam String confirmationCode) {
//        Map<String,String> response = new HashMap<>();
//        try{
//            passwordResetService.checkConfirmationCode(username,confirmationCode);
//            response.put("confirmationCode",confirmationCode);
//            response.put("username",username);
//            return ResponseEntity.ok(response);
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    @GetMapping("/auth/new-password")
    public String newPassword(@RequestParam String username,
                              @RequestParam String confirmationCode,
                              Model model) {
        model.addAttribute("username", username);
        model.addAttribute("confirmationCode", confirmationCode);
        return "auth/new-password-page";
    }

    //    @PostMapping("/auth/new-password")
//    public ResponseEntity<Map<String,String>> newPasswordPost(@RequestParam String username,
//                                                              @RequestParam String confirmationCode,
//                                                              @RequestParam String password,
//                                                              @RequestParam String confirmPassword){
//        Map<String,String> response = new HashMap<>();
//        try {
//            passwordResetService.resetPassword(username,confirmationCode,password,confirmPassword);
//            response.put("message", "Пароль успешно изменен!");
//            return ResponseEntity.ok(response);
//        }catch (Exception e){
//            e.printStackTrace();
//            response.put("error", e.getMessage());
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
    @PostMapping("/auth/sign-in")
    public ResponseEntity<Map<String, String>> singIn(@RequestParam String username,
                                                      @RequestParam String password) {
        Map<String, String> response = new HashMap<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            String token = jwtTokenProvider.generateToken(authentication);

            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

//    @PostMapping("/auth/register")
//    public String register(@ModelAttribute EmployeeCreate employeeCreate) {
//        System.out.println(employeeCreate.getLastname());
//        System.out.println(employeeCreate.getBranchId());
//        try {
//            userService.createTempUser(employeeCreate);
//
//            return "redirect:/login";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "redirect:/error?message1=" + e.getMessage();
//        }
//    }
}
