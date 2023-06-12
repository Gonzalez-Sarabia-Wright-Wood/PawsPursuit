package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Comment;
import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.CommentRepository;
import com.codeup.pawspursuit.repositories.PetRepository;
import com.codeup.pawspursuit.repositories.PostRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import com.codeup.pawspursuit.models.*;
import com.codeup.pawspursuit.repositories.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Controller
public class PetController {

    private PetRepository petDao;
    private PostRepository postDao;
    private UserRepository userDao;
    private CommentRepository commentDao;
    @Value("${filestack.api.key}")
    private String filestackapi;

    private CategoryRepository categoryDao;

    public PetController(PetRepository petDao, PostRepository postDao,
                         UserRepository userDao, CommentRepository commentDao, CategoryRepository categoryDao) {
        this.petDao = petDao;
        this.postDao = postDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping(path = "/pets")
    public String petsIndex(Model model) {
        model.addAttribute("pets", petDao.findAll());
        model.addAttribute("post", postDao);
        return "/pets/index";
    }

    @GetMapping(path = "/pets/{id}")
    public String onePet(@PathVariable Long id, Model model) {
        Pet onePet = petDao.findById(id).get();
        Post onePost = postDao.findByPetId(id);
        model.addAttribute("onePet", onePet);
        model.addAttribute("onePost", onePost);
        Comment comment = new Comment();
        List<Comment> commentList = commentDao.findAllByPostId(onePost.getId());
        model.addAttribute("comment", comment);
        model.addAttribute("commentList", commentList);
        return "/pets/show";
    }

    @GetMapping("/create")
    public String createPet(Model model) {
        List<Category>Categories = categoryDao.findAll();
        model.addAttribute("pet", new Pet());
        model.addAttribute("post", new Post());
        model.addAttribute("filestackapi", filestackapi);
        model.addAttribute("categories", Categories);
        return "/pets/create";
    }

    @PostMapping("/pets/create")
    public String savePet(@RequestParam String title, @RequestParam String body, @RequestParam String name, @RequestParam String species, @RequestParam String breed, @RequestParam String size, @RequestParam String description, @RequestParam String stashFilestackURL) {
        Post post = new Post();
        Pet pet = new Pet();
        pet.setUser((User) getContext().getAuthentication().getPrincipal());
        post.setUser((User) getContext().getAuthentication().getPrincipal());
        pet.setName(name);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setSize(size);
        pet.setDescription(description);
        petDao.save(pet);
        post.setTitle(title);
        post.setBody(body);
        post.setPet(petDao.findFirstByOrderByIdDesc());
        pet.setPhoto(stashFilestackURL);
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
        return "redirect:/pets";
    }

    @GetMapping("/pets/{id}/edit")
    public String editPet(Model model, @PathVariable Long id) {
        Pet pet = petDao.findById(id).get();
        Post post = postDao.findByPetId(id);
        if(post.getUser()==(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()) {
            model.addAttribute("pet", pet);
            model.addAttribute("post", post);
            return "/pets/edit";
        }else{
            return "redirect:/pets";
        }
    }

    @PostMapping("/pets/{id}/edit")
    public String updatePet(@RequestParam String title, @RequestParam String body, @RequestParam String name, @RequestParam String species, @RequestParam String breed, @RequestParam String size, @RequestParam String description, @RequestParam Long pet_id, @RequestParam Long post_id) {
        Pet pet = petDao.findById(pet_id).get();
        pet.setName(name);
        pet.setSpecies(species);
        pet.setBreed(breed);
        pet.setSize(size);
        pet.setDescription(description);
        petDao.save(pet);
        Post post = postDao.findById(post_id).get();
        post.setTitle(title);
        post.setBody(body);
        postDao.save(post);
        return "redirect:/pets";
    }

}
