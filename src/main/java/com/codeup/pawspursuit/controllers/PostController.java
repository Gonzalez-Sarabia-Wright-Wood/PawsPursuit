package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.*;
import com.codeup.pawspursuit.repositories.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private CommentRepository commentDao;
    private CategoryRepository categoryDao;
    @Value("${mapbox.api.key}")
    private String mapboxapikey;

    public PostController(PostRepository postRepository, UserRepository userRepository, PetRepository petRepository, CommentRepository commentRepository, CategoryRepository categoryDao) {
        this.postDao = postRepository;
        this.userDao = userRepository;
        this.petDao = petRepository;
        this.commentDao = commentRepository;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> posts = postDao.findPostsByPetNull();
        model.addAttribute("posts", posts);
        return "posts/forums";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        Post post = postDao.findById(id).get();
        List<Category> categoryList = post.getCategories();
        Comment comment = new Comment();
        List<Comment> commentList = commentDao.findAllByPostId(id);
        model.addAttribute("comment", comment);
        model.addAttribute("commentList", commentList);
        model.addAttribute("post", post);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("mapboxapi", mapboxapikey);
        model.addAttribute("petLocation", post.getLocation());

        return "posts/show";
    }

    private int getIndexOfPost(List<Post> postList, Long postId) {
        for (int i = 0; i < postList.size(); i++) {
            Post existingPost = postList.get(i);
            if (existingPost.getId().equals(postId)) {
                return i;
            }
        }
        return -1;
    }

    @PostMapping("/posts/create")
    public String submitPost(@ModelAttribute Post post, @RequestParam Category category, @RequestParam String lastSeen2) {
        post.getCategories().add(category);
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.findById(authenticatedUser.getId()).orElseThrow();
        post.setUser(user);
        post.setLocation(lastSeen2);
        postDao.save(post);
        return "redirect:posts";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postDao.findById(id).get();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (post.getUser().getId().equals(user.getId())) {
            List<Category> categories = categoryDao.findAll();
            Category category = post.getCategories().get(0);
            model.addAttribute("mapboxapi", mapboxapikey);
            model.addAttribute("category", category);
            model.addAttribute("categories", categories);
            model.addAttribute("post", post);
            return "posts/edit";
        } else{
            return "redirect:/posts";
        }
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@RequestParam Long id) {
        postDao.deleteById(id);
        return "redirect:posts";
    }

    @GetMapping("/aboutUs")
    public String aboutUs() {
        return "aboutUs";
    }
}