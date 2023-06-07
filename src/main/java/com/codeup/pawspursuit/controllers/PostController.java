package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.repositories.PostRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostRepository postDao;
    private UserRepository userDao;

    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postDao = postRepository;
        this.userDao = userRepository;
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "Posts/forums";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "Posts/show";
    }

    @GetMapping("/posts/create")
    public String createPost(Model model) {
        model.addAttribute("post", new Post());
        return "Pets/create";
    }

    @PostMapping("/posts/create")
    public String submitPost(@ModelAttribute Post post){
        postDao.save(post);
        return "redirect:/profile/1";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable Long id, Model model){
        Post post = postDao.findById(id).get();
        model.addAttribute("post", post);
        return "Pets/create";
    }
    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }
}
