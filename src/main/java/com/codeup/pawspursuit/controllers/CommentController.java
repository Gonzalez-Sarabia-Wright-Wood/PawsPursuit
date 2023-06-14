package com.codeup.pawspursuit.controllers;

import com.codeup.pawspursuit.models.Comment;
import com.codeup.pawspursuit.models.Post;
import com.codeup.pawspursuit.models.User;
import com.codeup.pawspursuit.repositories.CommentRepository;
import com.codeup.pawspursuit.repositories.PostRepository;
import com.codeup.pawspursuit.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    private CommentRepository commentDao;
    private PostRepository postDao;
    private UserRepository userDao;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentDao = commentRepository;
        this.postDao = postRepository;
        this.userDao = userRepository;
    }

    @PostMapping("/comment/post")
    public String submitPostComment(@ModelAttribute Comment comment) {
        comment.setPost(postDao.findById(comment.getPost().getId()).get());
        comment.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        commentDao.save(comment);
        return "redirect:/posts/" + comment.getPost().getId();
    }

    @PostMapping("/comment/pet")
    public String submitPetComment(@ModelAttribute Comment comment) {
        comment.setPost(postDao.findById(comment.getPost().getId()).get());
        comment.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        commentDao.save(comment);
        return "redirect:/pets/" + comment.getPost().getPet().getId();
    }

    @PostMapping("/comment/delete")
    public String deleteComment(@RequestParam Long id) {
        Long postId = postDao.findById(commentDao.findById(id).get().getPost().getId()).get().getId();
        commentDao.deleteById(id);
        if (postDao.findById(postId).get().getPet() != null) {
            return "redirect:/pets/" + postDao.findById(postId).get().getPet().getId();
        }
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/comment/edit")
    public String editComment(@RequestParam Long id, @RequestParam String body) {
        Comment comment = commentDao.findById(id).get();
        comment.setBody(body);
        commentDao.save(comment);
        if (postDao.findById(comment.getPost().getId()).get().getPet() != null) {
            return "redirect:/pets/" + postDao.findById(comment.getPost().getId()).get().getPet().getId();
        }
        return "redirect:/posts/" + comment.getPost().getId();
    }
}