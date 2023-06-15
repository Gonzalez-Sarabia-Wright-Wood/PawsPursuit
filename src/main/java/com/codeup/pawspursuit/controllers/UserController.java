package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.PetRepository;
import com.codeup.pawspursuit.repositories.PostRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private UserRepository userDao;
    private PetRepository petsDao;
    private PostRepository postDao;
    private PasswordEncoder passwordEncoder;

    @Value("${talkJs.test.app.id}")
    private String testAppId;

    public UserController(UserRepository userDao, PetRepository petsDao, PostRepository postDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.petsDao = petsDao;
        this.postDao = postDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String homePage(Model model) {
        Page<Pet> pets = petsDao.findAll(
                PageRequest.of(0, 4)
        );
        model.addAttribute("pets", pets);
        return "index";
    }
//
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
//            return "redirect:profile";
//        }
//        model.addAttribute("error", "Invalid username or password");
//        return "login";
//    }


    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute User user, Model model) {
        User existingUser = userDao.findByUsername(user.getUsername());
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        if (existingUser != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        userDao.save(user);
        return "redirect:login";
    }

    @GetMapping("/profile")
    public String viewOwnProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDb = userDao.getReferenceById(user.getId());
        List<Pet> pets = petsDao.findPetsByUserId(user.getId());
        List<Post> posts = postDao.findPostsByUserId(user.getId());
        model.addAttribute("user", userFromDb);
        model.addAttribute("pets", pets);
        model.addAttribute("posts", posts);
        return "user/profile";
    }

    @GetMapping("/profile/{id}")
    public String viewOtherUserProfile(Model model, @PathVariable Long id) {
        User user = userDao.findById(id).get();
        List<Pet> pets = petsDao.findPetsByUserId(user.getId());
        List<Post> posts = postDao.findPostsByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("pets", pets);
        model.addAttribute("posts", posts);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping(path = "/profile/edit")
    public String editUser(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userFromDb = userDao.getReferenceById(user.getId());
        model.addAttribute("user", userFromDb);
        return "user/edit";
    }

    @PostMapping(path = "/profile/edit")
    public String editUser(@ModelAttribute User user, @RequestParam String currentPW, @RequestParam String newPW) {
        User userFromDb = userDao.findById(user.getId()).get();

        if (!newPW.isEmpty()) {
            if (passwordEncoder.matches(currentPW, userFromDb.getPassword())) {
                String hash = passwordEncoder.encode(newPW);
                userFromDb.setPassword(hash);
            }
        }
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setEmail(user.getEmail());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userFromDb.setZipCode(user.getZipCode());
        userFromDb.setUsername(user.getUsername());
        userDao.save(userFromDb);
        return "redirect:profile";
    }

    @PostMapping("/profile/delete")
    public String deleteUserPost(@RequestParam Long id) {
        userDao.deleteById(id);
        return "redirect:login";
    }


    @GetMapping("/chat/{r_id}")
    public String showChat(Model model, @PathVariable Long r_id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User r_user = userDao.findById(r_id).get();
        model.addAttribute("testAppId", testAppId);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("id2", r_user.getId());
        model.addAttribute("name2", r_user.getName());
        model.addAttribute("email2", r_user.getEmail());

        return "user/chat";
    }

    @GetMapping("/chat")
    public String sendChat(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("testAppId", testAppId);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("email", user.getEmail());
        return "user/messages";
    }


}
