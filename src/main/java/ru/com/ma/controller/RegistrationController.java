package ru.com.ma.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.com.ma.domain.User;
import ru.com.ma.service.UserService;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model){
        User existsUser = userService.findUserByUserName(user.getUsername());

        if(existsUser != null){
            model.addAttribute("msg", "User exists!");
            return "registration";
        }

        userService.save(user);

        return "redirect:/login";
    }
}
