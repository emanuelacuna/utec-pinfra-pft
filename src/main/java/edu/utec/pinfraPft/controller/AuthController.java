package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.DepartmentDto;
import edu.utec.pinfraPft.dto.ItrDto;
import edu.utec.pinfraPft.dto.LocalityDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.DepartmentService;
import edu.utec.pinfraPft.service.ItrService;
import edu.utec.pinfraPft.service.LocalityService;
import edu.utec.pinfraPft.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;

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

        UserDto user = new UserDto();
        List<DepartmentDto> departments = departmentService.findAll();
        List<LocalityDto> localities = localityService.findAll();
        List<ItrDto> itrs = itrService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("departments", departments);
        model.addAttribute("localities", localities);
        model.addAttribute("itrs", itrs);

        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserDto user,
                           BindingResult result, Model model) {

        if(userService.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username already in use");
        }

        if(userService.existsByPersonalEmail(user.getPersonalEmail())) {
            result.rejectValue("personalEmail", "error.user", "Personal email already in use");
        }

        if(userService.existsByInstitutionalEmail(user.getInstitutionalEmail())) {
            result.rejectValue("institutionalEmail", "error.user", "Institutional email already in use");
        }

        if(userService.existsByDocument(user.getDocument())) {
            result.rejectValue("document", "error.user", "Document already in use");
        }

        if(result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("localities", localityService.findAll());
            model.addAttribute("itrs", itrService.findAll());
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
