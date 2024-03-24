package edu.utec.loginPinfra.controller;

import edu.utec.loginPinfra.dto.RegistrationDto;
import edu.utec.loginPinfra.service.UserServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserServiceImp userService;

    @Autowired
    public AuthController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result, Model model) {

        if(userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already in use");
        }

        if(userService.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Email already in use");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        } else {
            userService.register(user);
            return "redirect:/login?success";
        }
    }
}
