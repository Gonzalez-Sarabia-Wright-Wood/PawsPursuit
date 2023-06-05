package controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
public class PostController {
    @GetMapping
    public String getPosts() {
        return "Posts/forums";
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable long id) {
        return "Posts/show_case" + id;
    }

    @PostMapping("/create")
    @ResponseBody
    public String createPost() {
        return "create.html";
    }
}
