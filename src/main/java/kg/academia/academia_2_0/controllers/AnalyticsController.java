package kg.academia.academia_2_0.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {
    @GetMapping("/telegram")
    public String telegramAnalytics(){
        return "telegram-analytics";
    }
}
