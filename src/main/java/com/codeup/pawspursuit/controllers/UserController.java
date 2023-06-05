package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository userDao;

    public UserController(UserRepository userDao){
        this.userDao = userDao;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user){
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(Model model, @PathVariable Long id){
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        return "Users/profile";
    }

}
