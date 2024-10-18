package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.ClaimDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.ClaimService;
import edu.utec.pinfraPft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/claim")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;
    private final UserService userService;

    @PostMapping("/")
    public ClaimDto save(@RequestBody ClaimDto claimDto) {
        return claimService.save(claimDto);
    }

    @PutMapping("/{id}")
    public ClaimDto update(@PathVariable Long id, @RequestBody ClaimDto claimDto) {
        return claimService.update(id, claimDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        claimService.delete(id);
    }

    @GetMapping("/{id}")
    public ClaimDto findById(@PathVariable Long id) {
        return claimService.findById(id);
    }

    @GetMapping("/")
    public List<ClaimDto> findAll() {
        return claimService.findAll();
    }

    @GetMapping("/user/{id}")
    public List<ClaimDto> findAllByUserId(@PathVariable Long id) {
        return claimService.findAllByUserId(id);
    }

    @GetMapping("/studentsWithClaims")
    public List<UserDto> buscarEstudiantesConReclamos() {
        return userService.findStudentsWithClaims();
    }

    // Método para mostrar la página de creación de reclamos
    @GetMapping("/new")
    public String showNewClaimForm(Model model) {
        ClaimDto claimDto = new ClaimDto();
        model.addAttribute("claim", claimDto);
        return "newClaim";  // Especificar la ruta correcta de la plantilla
    }


    // Método para crear un nuevo reclamo
    @PostMapping("/create")
    public String createClaim(@ModelAttribute("claim") ClaimDto claimDto, Principal principal) {
        claimService.save(claimDto);
        return "redirect:/api/claim/list";
    }

    // Método para mostrar los reclamos del estudiante
    @GetMapping("/list")
    public String listStudentClaims(Model model, UserDto userDto) {
        List<ClaimDto> claims = claimService.findAllByUserId(userDto.getId());
        model.addAttribute("claims", claims);
        return "claims/listClaimStudent";
    }

    // Método para actualizar un reclamo
    @PostMapping("/update")
    public String updateClaim(@ModelAttribute("claim") ClaimDto claimDto) {
        claimService.update(claimDto.getId(),claimDto);
        return "redirect:/api/claim/list";
    }

    // Método para eliminar un reclamo
    @PostMapping("/delete")
    public String deleteClaim(@RequestParam("id") Long id) {
        claimService.delete(id);
        return "redirect:/api/claim/list";
    }


}
