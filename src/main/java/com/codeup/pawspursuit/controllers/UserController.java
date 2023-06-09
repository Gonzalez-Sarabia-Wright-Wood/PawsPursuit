package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    @Value("${talkJs.test.app.id}")
    private String testAppId;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;

    }

//    @GetMapping("/login")
//    public String userLoginGet() {
//        return "login";
//    }

//    @PostMapping("/login")
//    public String userLoginPost(@RequestParam String username, @RequestParam String password, Model model) {
//        User user = userDao.findByUsername(username);
//        if (user == null) {
//            return "redirect:/login";
//        } else if (user.getPassword().equals(password)) {
////            model.addAttribute("user", user);
//            return "redirect:/profile";
//        }
//        model.addAttribute("error", "Invalid username or password");
//        return "login";
//    }



    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model) {
        User existingUser = userDao.findByUsername(user.getUsername());
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        if (existingUser != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
            return "redirect:/login";

//        try {
//            userDao.save(user);
//            return "redirect:/login";
//        } catch (Exception e) {
//            model.addAttribute("error", "An error occurred during registration");
//            return "register";
//        }
    }
@GetMapping("/profile")
    public String viewOwnProfile(Model model){
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    model.addAttribute("user", user);

    return "User/profile";
}

    @GetMapping("/profile/{id}")
    public String viewOtherUserProfile(Model model, @PathVariable Long id) {

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


    @GetMapping("/chat/{r_id}")
    public String showChat(Model model, @PathVariable Long r_id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User r_user = userDao.findById(r_id).get();
        model.addAttribute("testAppId",  testAppId);
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
