package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.AttendanceDto;
import edu.utec.pinfraPft.dto.EventDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.service.AttendanceService;
import edu.utec.pinfraPft.service.EventService;
import edu.utec.pinfraPft.service.ItrService;
import edu.utec.pinfraPft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final EventService eventService;
    private final UserService userService;
    private final AttendanceService attendanceService;
    private final Constantes constantes = new Constantes();
    private final ItrService itrService;

    @GetMapping("/attendancesAnalist")
    public String listEvents(Model model) {
        AttendanceDto attendanceDto = new AttendanceDto();
        List<EventDto> events = eventService.findAll();
        List<UserDto> students = userService.getAllStudents();
        List<String> attendanceStatus = constantes.getAttendanceStatus();

        // Filtrar estudiantes que tengan al menos un evento pendiente de asistencia
        List<UserDto> filteredStudents = students.stream()
                .filter(student -> events.stream()
                        .anyMatch(event -> !attendanceService.hasAttendance(student.getId(), event.getId())))
                .collect(Collectors.toList());
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);
        model.addAttribute("students", filteredStudents);
        model.addAttribute("attendanceStatus", attendanceStatus);
        model.addAttribute("attendance", attendanceDto);

        return "attendances/attendanceRegisterAnalist";
    }

    @GetMapping("/attendancesTeacher")
    public String teacherListEvents(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        List<EventDto> events = eventService.findAllByUserId(userDto.getId());
        AttendanceDto attendanceDto = new AttendanceDto();
        List<UserDto> students = userService.getAllStudents();
        List<String> attendanceStatus = new ArrayList<>();

        // Filtrar estudiantes que tengan al menos un evento pendiente de asistencia
        List<UserDto> filteredStudents = students.stream()
                .filter(student -> events.stream()
                        .anyMatch(event -> !attendanceService.hasAttendance(student.getId(), event.getId())))
                .collect(Collectors.toList());
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);
        model.addAttribute("students", filteredStudents);
        model.addAttribute("attendanceStatus", attendanceStatus);
        model.addAttribute("attendance", attendanceDto);

        return "attendances/attendanceRegisterTeacher";
    }


    @PostMapping("/filter-events")
    @ResponseBody
    public Map<String, Object> filterEvents(@RequestParam Long studentId) {
        System.out.println(studentId);

        List<EventDto> allEvents = eventService.findAll();
        List<EventDto> filteredEvents = allEvents.stream()
                .filter(event -> !attendanceService.hasAttendance(studentId, event.getId()))
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("events", filteredEvents);
        response.put("hasEvents", !filteredEvents.isEmpty());

        return response;
    }

    @PostMapping("/register")
    public String registerAttendance(@ModelAttribute AttendanceDto attendanceDto) {
        // Lógica para guardar la asistencia
        attendanceService.save(attendanceDto);
        return "redirect:/attendances/attendancesAnalist";
    }

    @PostMapping("/registerByTeacher")
    public String registerAttendanceByTeacher(@ModelAttribute AttendanceDto attendanceDto) {
        // Lógica para guardar la asistencia
        attendanceService.save(attendanceDto);
        return "redirect:/attendances/attendancesAnalist";
    }


}
