package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.*;
import edu.utec.pinfraPft.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final DepartmentService departmentService;

    private final LocalityService localityService;

    private final ItrService itrService;

    private final ClaimService claimService;

    private final ActionTakenService actionTakenService;

    private final ReportService reportService;

    @GetMapping
    public String admin(Model model) {
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/dropout")
    public String dropout(@RequestParam Long id) {
        userService.changeUserStatus(id);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateFrom(@PathVariable Long id, Model model) {
        UserDto userDto = userService.findUserDtoById(id);
        model.addAttribute("user", userDto);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("localities", localityService.findAll());
        model.addAttribute("itrs", itrService.findAll());
        return "user-update";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("localities", localityService.findAll());
            model.addAttribute("itrs", itrService.findAll());
            return "user-update";
        } else {
            userService.updateUser(userDto);
            return "redirect:/admin";
        }
    }

    @GetMapping("/claim")
    public String claims(Model model) {
        List<ClaimDto> claims = claimService.findAll();
        model.addAttribute("userService", userService);
        model.addAttribute("claims", claims);
        return "claim";
    }

    @PostMapping("/claim/status")
    public String changeClaimStatus(@RequestParam Long id, @RequestParam String status) {
        ClaimDto claimDto = claimService.findById(id);
        claimService.changeStatus(id, status);
        return "redirect:/admin/claim";
    }

    @PostMapping("/claim/actionTaken")
    public String changeClaimStatusAndRegisterAction(@RequestParam Long claimIdInput,
                                                     @RequestParam String status,
                                                     @RequestParam String actionTaken) {
        // Cambiar el estado del reclamo
        claimService.changeStatus(claimIdInput, status);

        UserDto userDto = userService.findUserDtoByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName());

        // Registrar la acción en Attendance
        ActionTakenDto actionTakenDto = new ActionTakenDto();
        actionTakenDto.setClaim(claimIdInput); // Asignar el reclamo asociado
        actionTakenDto.setAdmin(userDto.getId()); // Asignar el admin que realizo la accion
        actionTakenDto.setStatus(status);
        actionTakenDto.setActionTaken(actionTaken); // Registrar la acción tomada

        actionTakenService.save(actionTakenDto); // Guardar en la base de datos

        return "redirect:/admin/claim";
    }


    @GetMapping("/reports/justifications")
    public String getJustificationsByStudent(@RequestParam Long student_r1,
                                             @RequestParam(required = false, defaultValue = "justifications") String activeTab,
                                             Model model) {
        List<JustificationDto> justifications = reportService.findJustificationsByStudentId(student_r1);
        List<UserDto> users = userService.getAllStudents();
        model.addAttribute("students_r1", users);
        model.addAttribute("students_r2", users);
        model.addAttribute("students_r3", users);
        model.addAttribute("justifications", justifications);
        model.addAttribute("activeTab", activeTab);
        return "/reports/reports";
    }

    @GetMapping("/reports/events")
    public String getEventsByStudent(@RequestParam Long student_r3,
                                     @RequestParam(required = false, defaultValue = "events") String activeTab,
                                     Model model) {
        List<EventDto> events = reportService.findEventsByStudentId(student_r3);

        List<UserDto> users = userService.getAllStudents();
        model.addAttribute("students_r1", users);
        model.addAttribute("students_r2", users);
        model.addAttribute("students_r3", users);
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("events", events);
        return "/reports/reports";
    }

    @GetMapping("/reports/constancies")
    public String getConstanciesByStudent(@RequestParam Long student_r2,
                                          @RequestParam(required = false, defaultValue = "constancies") String activeTab,
                                          Model model) {
        List<ConstancyDto> constancies = reportService.findConstanciesByStudentId(student_r2);

        List<UserDto> users = userService.getAllStudents();
        model.addAttribute("students_r1", users);
        model.addAttribute("students_r2", users);
        model.addAttribute("students_r3", users);
        model.addAttribute("activeTab", activeTab);
        model.addAttribute("constancies", constancies);
        return "/reports/reports";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {
        List<UserDto> users = userService.getAllStudents();
        model.addAttribute("students_r1", users);
        model.addAttribute("students_r2", users);
        model.addAttribute("students_r3", users);
        return "/reports/reports";
    }
}
