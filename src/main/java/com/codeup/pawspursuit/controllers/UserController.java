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
        return "/login";
    }

    @PostMapping("/login")
    public String userLoginPost(@RequestParam String username, Model model) {
        User user = userDao.findUserByUsername(username);
        model.addAttribute("user", user);
        return "redirect:/profile/" + user.getId();
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user) {
        userDao.save(user);
        return "redirect:/login";
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

    @GetMapping("/chat/{id}")
    public String showChat(Model model, @PathVariable Long id) {
        User user = userDao.getReferenceById(id);
        model.addAttribute("testAppId", testAppId);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());

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
