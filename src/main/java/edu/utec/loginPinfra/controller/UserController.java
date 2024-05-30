package edu.utec.loginPinfra.controller;

import edu.utec.loginPinfra.dto.CareerDto;
import edu.utec.loginPinfra.dto.UpdateDto;
import edu.utec.loginPinfra.model.UserEntity;
import edu.utec.loginPinfra.service.CareerServiceImpl;
import edu.utec.loginPinfra.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImp userServiceImp;

    private final CareerServiceImpl careerService;

    @PostMapping("/dropout")
    public String createUser(@RequestParam Long id) {
        userServiceImp.changeUserStatus(id);
        return "redirect:/login?logout";
    }

    @GetMapping("/update")
    public String getUpdateForm(Model model) {
        UpdateDto updateDto = userServiceImp
                .getUserUpdateDto(SecurityContextHolder.getContext().getAuthentication().getName());
        model.addAttribute("user", updateDto);
        List<CareerDto> careers = careerService.getCareers();
        model.addAttribute("careers", careers);
        return "user-update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UpdateDto updateDto) {
        userServiceImp.updateUser(updateDto);
        return "redirect:/home";
    }

}
