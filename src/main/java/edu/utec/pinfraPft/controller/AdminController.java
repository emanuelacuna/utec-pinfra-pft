package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.CareerServiceImpl;
import edu.utec.pinfraPft.service.UserServiceImp;
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

    private final UserServiceImp userServiceImp;

    private final CareerServiceImpl careerService;

    @GetMapping()
    public String admin(Model model) {
        List<UserDto> users = userServiceImp.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam Long id) {
        userServiceImp.changeUserStatus(id);
        return "redirect:/admin";
    }

}
