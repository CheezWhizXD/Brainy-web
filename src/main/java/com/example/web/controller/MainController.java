package com.example.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Главная страница
    @GetMapping("/")
    public String homePage() {
        return "index"; // отдаёт templates/index.html
    }

    // Страница "Об авторе"
    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // templates/about.html
    }

    // Страница логина
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }
}
