package ru.timofey.NauJava.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping({"/logout", "/logout-page"})
    public String logoutPage() {
        return "security/logout";
    }
}