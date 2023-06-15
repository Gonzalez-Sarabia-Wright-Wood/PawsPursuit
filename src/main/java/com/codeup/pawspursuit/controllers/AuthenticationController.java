package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthenticationController {

    private UserRepository userDao;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

}
