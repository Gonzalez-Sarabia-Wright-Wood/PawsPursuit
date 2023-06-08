package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Pet;
import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.PetRepository;
import com.codeup.pawspursuit.repositories.PostRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    private PostRepository postDao;
    private PetRepository petDao;
    private UserRepository userDao;

    public PostController(PostRepository postRepository, UserRepository userRepository, PetRepository petRepository) {
        this.postDao = postRepository;
        this.userDao = userRepository;
        this.petDao = petRepository;
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> posts = postDao.findPostsByPetNull();
        model.addAttribute("posts", posts);
        return "Posts/forums";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "Posts/show";
    }

//    @GetMapping("/create")
//    public String createPost(Model model) {
//        model.addAttribute("post", new Post());
//        return "Pets/create";
//    }

    @PostMapping("/posts/create")
    public String submitPost(@ModelAttribute Post post) {
        User user = userDao.findById(1L).get();
        List<Post> postList = user.getPosts();
        postList.add(post);
        userDao.save(user);
        return "redirect:/profile/1";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "Posts/edit";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@RequestParam Long id){
        Post post = postDao.findById(id).get();
        User user = userDao.findById(1L).get();
        user.getPosts().remove(post);
        post.getUsers().remove(user);
        userDao.save(user);
        postDao.save(post);

        postDao.deleteById(id);
        return "redirect:/posts";
    }

//    @GetMapping("/")
//    public String index(Model model) {
//        List<Pet> pet = petDao.findAll();
//        model.addAttribute("post", pet);
//        return "/index";
//    }
    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }
}
