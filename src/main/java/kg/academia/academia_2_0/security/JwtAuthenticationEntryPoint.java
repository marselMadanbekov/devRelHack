package kg.academia.academia_2_0.security;


//import com.example.academia1_1.domain.payload.response.InvalidLoginResponse;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String redirectPage;
        if (authException instanceof BadCredentialsException) {
            // Обработка ошибки неверных учетных данных (логин/пароль)
            redirectPage = "/error/auth-error?error=0"; // Направляем на страницу с URL "/error1"
        }
        else if(authException instanceof DisabledException){
            redirectPage = "/error/auth-error?error=1";
        }
        else {
            // Обработка других ошибок аутентификации
            redirectPage = "/login"; // Направляем на общую страницу ошибки
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect(redirectPage);
    }
}
