package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.ConstancyDto;
import edu.utec.pinfraPft.dto.EventDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/constancy")
@RequiredArgsConstructor
public class ConstancyController {

    private final EventService eventService;
    private final UserService userService;
    private final Constantes constantes = new Constantes();
    private final ConstancyService constancyService;
    private final AttendanceService attendanceService;


    @GetMapping("/new")
    public String showNewConstancyForm(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);

        ConstancyDto constancyDto = new ConstancyDto();
        List<String> constancyTypes = constantes.getConstancyTypes();
        List<EventDto> allEvents = eventService.findAll();
        List<EventDto> filteredEvents = allEvents.stream()
                .filter(event -> attendanceService.hasAttendance(userDto.getId(), event.getId()))
                .collect(Collectors.toList());
        model.addAttribute("constancyTypes", constancyTypes);
        model.addAttribute("events", filteredEvents);
        model.addAttribute("constancy", constancyDto);
        return "constancies/newConstancy";
    }


    @PostMapping("/create")
    public String createConstancy(@ModelAttribute("constancy") ConstancyDto constancyDto, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        constancyDto.setStudent(userDto.getId());
        constancyDto.setStatus(constantes.getConstancyStatus().get(0));
        constancyDto.setDate(LocalDateTime.now());
        constancyService.save(constancyDto);
        return "redirect:/constancy/myList";
    }

    @GetMapping("/list")
    public String listConstancies(Model model) {
       List<ConstancyDto> constancies = constancyService.findAll();
       List<EventDto> allEvents = eventService.findAll();
       for (ConstancyDto constancy : constancies) {
           UserDto constancyStudent = userService.findUserDtoByUsername(userService.getUsernameById(constancy.getStudent()));
           constancy.setStudentName(constancyStudent.getFirstName()+" "+constancyStudent.getFirstSurname()+" "+constancyStudent.getSecondSurname());
           for(EventDto event : allEvents){
               if(constancy.getEvent().equals(event.getId())){
                   constancy.setEventTitle(event.getTitle());
               }
           }

       }
       model.addAttribute("constancies", constancies);

       return "constancies/listConstancy";
    }


    @PostMapping("/update")
    public String updateConstancy(@ModelAttribute("constancy") ConstancyDto constancyDto) {
        constancyDto.setDate(LocalDateTime.now());
        constancyService.update(constancyDto.getId(), constancyDto);
        return "redirect:/constancy/myList";
    }

    // Método para eliminar un reclamo
    @PostMapping("/delete")
    public String deleteConstancy(@RequestParam("id") Long id) {
        constancyService.delete(id);
        return "redirect:/constancy/myList";
    }

    @GetMapping("/myList")
    public String listConstanciesStudent(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        List<EventDto> allEvents = eventService.findAll();
        List<EventDto> filteredEvents = allEvents.stream()
                .filter(event -> attendanceService.hasAttendance(userDto.getId(), event.getId()))
                .collect(Collectors.toList());
        List<ConstancyDto> constancies = constancyService.findConstancyByStudent_Id(userDto.getId());

        List<String> constancyTypes = constantes.getConstancyTypes();

        for (ConstancyDto constancy : constancies) {
            for(EventDto event : filteredEvents){
                if(constancy.getEvent().equals(event.getId())){
                    constancy.setEventTitle(event.getTitle());
                }
            }

        }

        model.addAttribute("constancies", constancies);
        model.addAttribute("constancyTypes", constancyTypes);
        model.addAttribute("events", filteredEvents);

        return "constancies/listConstancyStudent";
    }
}
