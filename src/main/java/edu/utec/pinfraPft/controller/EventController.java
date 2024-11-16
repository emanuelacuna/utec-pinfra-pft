package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.EventDto;
import edu.utec.pinfraPft.dto.ItrDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.EventService;
import edu.utec.pinfraPft.service.ItrService;
import edu.utec.pinfraPft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final Constantes constantes = new Constantes();
    private final ItrService itrService;

    // Método para mostrar la página de creación de eventos
    @GetMapping("/new")
    public String showNewEventForm(Model model) {
        EventDto eventDto = new EventDto();
        List<UserDto> teachers = userService.getAllTeachers();
        List<String> eventStatus = constantes.getEventStatus();
        List<String> eventModes = constantes.getModes();
        List<String> eventTypes = constantes.getEventTypes();
        List<ItrDto> itrs = itrService.findAll();
        model.addAttribute("teachers", teachers);
        model.addAttribute("eventStatus", eventStatus);
        model.addAttribute("eventModes", eventModes);
        model.addAttribute("eventTypes", eventTypes);
        model.addAttribute("itrs", itrs);
        model.addAttribute("event", eventDto);
        return "events/newEvent";
    }


    // Método para crear un nuevo evento
    @PostMapping("/create")
    public String createEvent(@ModelAttribute("event") EventDto eventDto, Model model) {
        eventService.save(eventDto);
        return "redirect:/event/list";
    }

    @GetMapping("/list")
    public String listEvents(Model model) {
        List<EventDto> events = eventService.findAll();
        List<UserDto> teachers = userService.getAllTeachers();
        List<String> eventStatus = constantes.getEventStatus();
        List<String> eventModes = constantes.getModes();
        List<String> eventTypes = constantes.getEventTypes();
        List<ItrDto> itrs = itrService.findAll();
        for (EventDto event : events) {
            StringBuilder teacherNames = new StringBuilder(); // Lista para almacenar los nombres de los teachers

            for (Long teacherId : event.getTeachers()) {
                UserDto user = userService.findUserDtoById(teacherId);
                if (user != null) {
                    teacherNames.append(user.getFirstName()).append(" ").append(user.getSecondName()).append(" ; ");
                }
            }
            if (!teacherNames.isEmpty()) {
                teacherNames.setLength(teacherNames.length() - 2); // -2 para eliminar el último " ; "
            }

            event.setTeacherNames(teacherNames.toString());
            event.setItrName(itrService.findById(event.getItr()).getName());
        }



        model.addAttribute("teachers", teachers);
        model.addAttribute("eventStatus", eventStatus);
        model.addAttribute("eventModes", eventModes);
        model.addAttribute("eventTypes", eventTypes);
        model.addAttribute("itrs", itrs);
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);

        return "events/eventsList";
    }


    // Método para actualizar un reclamo
    @PostMapping("/update")
    public String updateEvent(@ModelAttribute("event") EventDto eventDto) {
        eventService.update(eventDto.getId(),eventDto);
        return "redirect:/event/list";
    }

    // Método para eliminar un reclamo
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam("id") Long id) {
        eventService.delete(id);
        return "redirect:/event/list";
    }

    @GetMapping("/mylist")
    public String teacherListEvents(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        List<EventDto> events = eventService.findAllByUserId(userDto.getId());
        for (EventDto event : events) {
            event.setItrName(itrService.findById(event.getItr()).getName());
        }
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);

        return "events/teacherEventsList";
    }
}
