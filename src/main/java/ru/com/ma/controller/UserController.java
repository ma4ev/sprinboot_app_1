package ru.com.ma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.com.ma.domain.Role;
import ru.com.ma.domain.User;
import ru.com.ma.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final String BASE_PATH = "admin_panel";

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String getUserList(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("isEmpty", users.isEmpty());
        model.addAttribute("users", users);
        return BASE_PATH + "/index";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String saveUser(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);
        userService.save(user);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String getEditForm(@PathVariable User user, Model model){
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return BASE_PATH + "/userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("delete/{user}")
    public String deleteUser(@ModelAttribute User user){
        userService.delete(user);
        return "redirect:/users";
    }

    @GetMapping("profile")
    public String profile(Model model, @AuthenticationPrincipal(expression = "user") User user){
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal(expression = "user") User user,
            @RequestParam String password,
            @RequestParam String email){
            userService.updateProfile(user, password, email);
            return "redirect:/users/profile";
    }
}
