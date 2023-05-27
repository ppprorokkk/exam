package com.example.ex2.controllers;

import com.example.ex2.models.Post;
import com.example.ex2.models.Users;
import com.example.ex2.repo.PostRepo;
import com.example.ex2.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;


@Controller
public class BlogController {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UsersRepo usersRepo;
    public class First {
        public static <T> T getFirstElementOf(Iterable<T> iterable) {
            return iterable.iterator().next();
        }/*  w  w  w.java2 s.  c om*/
    }
    //private UserService userService;
   // @Autowired
    //public BlogController(UserService userService) {
   //     this.userService = userService;
   // }

    @GetMapping("/login")
    public String login(Model model){

        return "login";

    }

    @PostMapping("/login")
    public String postLogin(@RequestParam String email, @RequestParam String pass) {
        // String email = name;

            Iterable<Users> userExists = usersRepo.FingByName(email);
    if (First.getFirstElementOf(userExists) == null){
        return "login";
    }
    else {
        Users users = First.getFirstElementOf(userExists);

        if (pass.equals(users.getPassword())) {
            return "redirect:/blog";
        } else {
            return "login";
        }

    }

    }


    @GetMapping("/register")
    public String register(Model model){

        return "register";

    }

    @PostMapping("/register")
    public String Postregister(@RequestParam String name,@RequestParam String email,@RequestParam String pass, Model model){
        Users users = new Users(name, email, pass);
        usersRepo.save(users);
        return "redirect:/login";
    }

    @GetMapping("/blog")
    public String blogMain(Model model){
        Iterable<Post> posts = postRepo.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";

    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model){
        return "blog-add";

    }
    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,@RequestParam String full_text, Model model){
        Post post = new Post(title, full_text);
        postRepo.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model){
    if (!postRepo.existsById(id)){
        return "redirect/blog";
    }

       Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> obj = new ArrayList<>();
        post.ifPresent(obj::add);
       model.addAttribute("post", obj);
        return "blog-det";

    }
    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model){
        if (!postRepo.existsById(id)){
            return "redirect/blog";
        }

        Optional<Post> post = postRepo.findById(id);
        ArrayList<Post> obj = new ArrayList<>();
        post.ifPresent(obj::add);
        model.addAttribute("post", obj);
        return "blog-edit";

    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title,@RequestParam String full_text, Model model){
        Post post = postRepo.findById(id).orElseThrow();
        post.setTitle(title);
        post.setFull_text(full_text);
        postRepo.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepo.findById(id).orElseThrow();

        postRepo.delete(post);
        return "redirect:/blog";
    }

}
