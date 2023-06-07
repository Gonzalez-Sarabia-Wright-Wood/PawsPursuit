package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class UserController {

    private UserRepository userDao;
    @Value("${talkJs.test.app.id}")
    private String testAppId;

    public UserController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String userLoginGet() {
        return "login";
    }

    @PostMapping("/login")
    public String userLoginPost(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userDao.findUserByUsername(username);
        if (user == null) {
            return "redirect:/login";
        } else if (user.getPassword().equals(password)) {
            model.addAttribute("user", user);
            return "redirect:/profile/" + user.getId();
        }
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model) {
        User existingUser = userDao.findUserByUsername(user.getUsername());

        if (existingUser != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        try {
            userDao.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during registration");
            return "register";
        }
    }


    @GetMapping("/profile/{id}")
    public String viewProfile(Model model, @PathVariable Long id) {
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        return "User/profile";
    }

    @GetMapping(path = "/profile/{id}/edit")
    public String editUser(Model model, @PathVariable Long id) {
        User user = userDao.findById(id).get();
        model.addAttribute("user", user);
        return "User/edit";
    }

    @PostMapping("/profile/{id}/delete")
    public String deleteUserPost(@RequestParam Long id) {
        userDao.deleteById(id);
        return "redirect:/login";
    }

    @GetMapping("/chat/{s_id}/{r_id}")
    public String showChat(Model model, @PathVariable Long s_id, @PathVariable Long r_id) {
        User user = userDao.findById(s_id).get();
        User r_user = userDao.findById(r_id).get();
        model.addAttribute("testAppId", testAppId);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("id2", r_user.getId());
        model.addAttribute("name2", r_user.getName());
        model.addAttribute("email2", r_user.getEmail());
        return "User/chat";
    }

    @PostMapping("/chat")
    public String sendChat() {
        return "User/chat";
    }

    @GetMapping("/profile/messages")
    public String showAllMessages() {
        return "User/messages";
    }
}
