package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.PetRepository;
import com.codeup.pawspursuit.repositories.PostRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {

    private PetRepository petDao;
    private PostRepository postDao;
    private final UserRepository userDao;

    public PetController(PetRepository petDao, PostRepository postDao,
                         UserRepository userDao) {
        this.petDao = petDao;
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "/pets")
    public String petsIndex(Model model) {
        model.addAttribute("pets", petDao.findAll());
        return "/Pets/index";
    }

    @GetMapping(path = "/pets/{id}")
    public String onePet(@PathVariable Long id, Model model) {
        Pet onePet = petDao.findById(id).get();
        Post onePost = postDao.findByPetId(id);
        model.addAttribute("onePet", onePet);
        model.addAttribute("onePost", onePost);
        return "/Pets/show";
    }

    @GetMapping("/pets/create")
    public String createPet(Model model) {
        model.addAttribute("pet", new Pet());
        model.addAttribute("post", new Post());
        return "/Pets/create";
    }

    @PostMapping("/pets/create")
    public String savePet(@RequestParam String title, @RequestParam String body, @RequestParam String name, @RequestParam String species, @RequestParam String breed, @RequestParam String size, @RequestParam String description) {
        Post post = new Post();
        Pet pet  = new Pet();
        pet.setUser(userDao.findById(1L).get());
        pet.setName(name);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setSize(size);
        pet.setDescription(description);
        petDao.save(pet);
        post.setTitle(title);
        post.setBody(body);
        post.setPet(petDao.findFirstByOrderByIdDesc());
        postDao.save(post);
        return "redirect:/profile/1";
    }

    @GetMapping("/pets/{id}/delete")
    public String deletePetGet() {
        return "redirect:/Users/profile";
    }

    @PostMapping("/pets/{id}/delete")
    public String deletePetPost(@RequestParam Long id) {
        petDao.deleteById(id);
        return "redirect:/Users/profile";
    }

    @GetMapping("/pets/{id}/edit")
    public String editPet(Model model, @PathVariable Long id) {
        Pet pet = petDao.findById(id).get();
        model.addAttribute("pet", pet);
        return "/Pets/edit";
    }
}
