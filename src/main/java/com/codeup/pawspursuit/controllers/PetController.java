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

    private final PetRepository petDao;
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final CommentRepository commentDao;
    @Value("${filestack.api.key}")
    private String filestackapi;
    @Value("${mapbox.api.key}")
    private String mapboxapikey;

    private final CategoryRepository categoryDao;

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
        return "pets/index";
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
        model.addAttribute("mapboxapi", mapboxapikey);
        model.addAttribute("petLocation", onePost.getLocation());
        return "pets/show";
    }

    @GetMapping("/create")
    public String createPet(Model model) {
        List<Category>Categories = categoryDao.findAll();
        model.addAttribute("pet", new Pet());
        model.addAttribute("post", new Post());
        model.addAttribute("filestackapi", filestackapi);
        model.addAttribute("mapboxapi", mapboxapikey);
        model.addAttribute("categories", Categories);
        return "pets/create";
    }

    @PostMapping("/pets/create")
    public String savePet(@RequestParam String title, @RequestParam String body, @RequestParam String name, @RequestParam String breed, @RequestParam String size, @RequestParam String description, @RequestParam String stashFilestackURL, @RequestParam String lastSeen, @RequestParam Category category){
        Post post = new Post();
        Pet pet = new Pet();
        pet.setUser((User) getContext().getAuthentication().getPrincipal());
        post.setUser((User) getContext().getAuthentication().getPrincipal());
        pet.setName(name);
        pet.setBreed(breed);
        pet.setSize(size);
        pet.setDescription(description);
        pet.setPhoto(stashFilestackURL);
        petDao.save(pet);
        post.setPet(petDao.findFirstByOrderByIdDesc());
        post.setTitle(title);
        post.setBody(body);
        post.setLocation(lastSeen);
        post.getCategories().add(category);
        postDao.save(post);
        return "redirect:/profile";
    }

    @GetMapping("/pets/{id}/delete")
    public String deletePetGet() {
        return "redirect:/profile";

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
        List<Category> categories = categoryDao.findAll();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (post.getUser().getId().equals(user.getId())) {
            model.addAttribute("pet", pet);
            model.addAttribute("post", post);
            model.addAttribute("categories", categories);
            model.addAttribute("filestackapi", filestackapi);
            model.addAttribute("mapboxapi", mapboxapikey);
            return "pets/edit";
        }else{
            return "redirect:/pets";
        }
    }

    @PostMapping("/pets/{id}/edit")
    public String updatePet(@RequestParam String title, @RequestParam String body, @RequestParam String name, @RequestParam Category category, @RequestParam String breed, @RequestParam String size, @RequestParam String description, @RequestParam Long pet_id, @RequestParam Long post_id, @RequestParam String stashFilestackURL, @RequestParam String lastSeen) {
        Pet pet = petDao.findById(pet_id).get();
        pet.setName(name);
        pet.setSpecies(category.getName());
        pet.setBreed(breed);
        pet.setSize(size);
        pet.setDescription(description);
        if(!stashFilestackURL.equals("replaceme")){
            pet.setPhoto(stashFilestackURL);
        }
        petDao.save(pet);
        Post post = postDao.findById(post_id).get();
        post.setTitle(title);
        post.setBody(body);
        post.getCategories().add(category);
        if(!lastSeen.equals("replaceme")) {
            post.setLocation(lastSeen);
        }
        postDao.save(post);
        return "redirect:/pets";
    }

}
