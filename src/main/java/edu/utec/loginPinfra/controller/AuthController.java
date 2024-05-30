package edu.utec.loginPinfra.controller;

import edu.utec.loginPinfra.dto.CareerDto;
import edu.utec.loginPinfra.dto.RegistrationDto;
import edu.utec.loginPinfra.model.Career;
import edu.utec.loginPinfra.service.CareerServiceImpl;
import edu.utec.loginPinfra.service.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    private final UserServiceImp userService;

    private final CareerServiceImpl careerService;

    @Autowired
    public AuthController(UserServiceImp userService, CareerServiceImpl careerService) {
        this.userService = userService;
        this.careerService = careerService;
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        HttpServletRequest request,
                        Model model) {
        if (error != null) {
            Exception exception = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            model.addAttribute("errorMessage", exception != null ? exception.getMessage() : "Unknown error");
        }
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        RegistrationDto user = new RegistrationDto();
        List<CareerDto> careers = careerService.getCareers();
        model.addAttribute("careers", careers);
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

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
