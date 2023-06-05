package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class UserController{

    private UserRepository userDao;

    public UserController(UserRepository userDao){
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String userLoginGet(){
        return "/login";
    }
    @PostMapping("login")
    public String userLoginPost(@PathVariable Long id, Model model){
        User user = userDao.findById(id).get();
        user.getId();
        model.addAttribute("user", user);
        return "/User/profile";
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
        return "User/profile";
    }

    @GetMapping(path = "/User/{id}/edit")
    public String editUser(Model model, @PathVariable Long id) {
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        return "User/profile";
    }

    @PostMapping("/User/{id}/delete")
    public String deleteUserPost(@RequestParam Long id){
        userDao.deleteById(id);
        return "redirect:/login";
    }

}
