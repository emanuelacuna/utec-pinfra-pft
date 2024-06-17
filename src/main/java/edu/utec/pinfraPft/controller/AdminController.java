package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.DepartmentService;
import edu.utec.pinfraPft.service.ItrService;
import edu.utec.pinfraPft.service.LocalityService;
import edu.utec.pinfraPft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;

    @GetMapping
    public String admin(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id) {
        userService.changeUserStatus(id);
        return "redirect:/admin";
    }
}
