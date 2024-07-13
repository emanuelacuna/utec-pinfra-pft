package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.ClaimDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.ClaimService;
import edu.utec.pinfraPft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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


}
