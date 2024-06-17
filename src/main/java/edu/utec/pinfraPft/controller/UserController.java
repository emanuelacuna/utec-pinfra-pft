package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.DepartmentService;
import edu.utec.pinfraPft.service.ItrService;
import edu.utec.pinfraPft.service.LocalityService;
import edu.utec.pinfraPft.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;

    @PostMapping("/dropout")
    public String dropout(@RequestParam Long id) {
        userService.changeUserStatus(id);
        return "redirect:/login?logout";
    }

    @GetMapping("/update")
    public String update(Model model) {

        UserDto userDto = userService.findUserDtoByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("user", userDto);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("localities", localityService.findAll());
        model.addAttribute("itrs", itrService.findAll());
        return "user-update";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("localities", localityService.findAll());
            model.addAttribute("itrs", itrService.findAll());
            return "user-update";
        } else {
            userService.updateUser(userDto);
            return "redirect:/user/update?success";
        }
    }

}
