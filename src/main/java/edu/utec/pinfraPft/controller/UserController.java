package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.ClaimDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.model.UserEntity;
import edu.utec.pinfraPft.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ClaimService claimService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;
    private final AuthController authController;

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

    // Método para mostrar la página de creación de reclamos
    @GetMapping("/claim/new")
    public String showNewClaimForm(Model model) {
        ClaimDto claimDto = new ClaimDto();
        model.addAttribute("claim", claimDto);
        return "claims/newClaim";  // Especificar la ruta correcta de la plantilla
    }


    // Método para crear un nuevo reclamo
    @PostMapping("/claim/create")
    public String createClaim(@ModelAttribute("claim") ClaimDto claimDto, Model model) {
        // Obtener el UserDto correspondiente al usuario autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        claimDto.setUser(userDto.getId());
        claimDto.setCreated(LocalDateTime.now());
        claimDto.setUpdated(LocalDateTime.now());
        claimService.save(claimDto);
        return "redirect:/user/claim/list";
    }

    @GetMapping("/claim/list")
    public String listStudentClaims(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        List<ClaimDto> claims = claimService.findAllByUserId(userDto.getId());

        // Pasar la lista de reclamos a la vista
        model.addAttribute("claims", claims);

        return "claims/listClaimStudent"; // La vista donde se mostrarán los reclamos
    }


    // Método para actualizar un reclamo
    @PostMapping("/claim/update")
    public String updateClaim(@ModelAttribute("claim") ClaimDto claimDto) {
        claimDto.setUpdated(LocalDateTime.now());
        claimService.update(claimDto.getId(),claimDto);
        return "redirect:/user/claim/list";
    }

    // Método para eliminar un reclamo
    @PostMapping("/claim/delete")
    public String deleteClaim(@RequestParam("id") Long id) {
        claimService.delete(id);
        return "redirect:/user/claim/list";
    }

}
