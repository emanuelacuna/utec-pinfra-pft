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

    @GetMapping("/attendancesAnalist")
    public String listEvents(Model model) {
        List<AttendanceDto> attendances = attendanceService.findAll();
        List<AttendanceDto> attendancesFiltradas = attendances.stream()
                .filter(attendance -> attendance.getStatus().isEmpty()) // Filtrar por status vacío
                .collect(Collectors.toList());

        List<String> attendanceStatus = constantes.getAttendanceStatus();

        for (AttendanceDto attendance : attendancesFiltradas) {
            UserDto user = userService.findUserDtoById(attendance.getStudent());
            attendance.setNombreEvento(eventService.findById(attendance.getEvent()).getTitle());
            attendance.setNombreStudent(user.getFirstName()+" "+user.getFirstSurname()+" "+user.getSecondSurname());
        }
        // Pasar la lista de reclamos a la vista
        model.addAttribute("attendances", attendancesFiltradas);
        model.addAttribute("attendanceStatus", attendanceStatus);

        return "attendances/attendanceRegisterAnalist";
    }

    @GetMapping("/attendancesTeacher")
    public String teacherListEvents(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.findUserDtoByUsername(username);
        Long teacherId = userDto.getId(); // Suponiendo que el ID del teacher está en el UserDto

        // Obtener todas las asistencias y filtrar por status vacío y eventos donde el teacher esté en la lista
        List<AttendanceDto> attendances = attendanceService.findAll();
        List<AttendanceDto> attendancesFiltradas = attendances.stream()
                .filter(attendance -> attendance.getStatus().isEmpty()) // Filtrar por status vacío
                .filter(attendance -> eventService.findById(attendance.getEvent()).getTeachers().stream()
                        .anyMatch(teacher -> teacher.equals(teacherId))) // Filtrar si el teacher está en la lista
                .collect(Collectors.toList());

        List<String> attendanceStatus = constantes.getAttendanceStatus();
        for (AttendanceDto attendance : attendancesFiltradas) {
            UserDto user = userService.findUserDtoById(attendance.getStudent());
            attendance.setNombreEvento(eventService.findById(attendance.getEvent()).getTitle());
            attendance.setNombreStudent(user.getFirstName()+" "+user.getFirstSurname()+" "+user.getSecondSurname());
        }
        // Pasar la lista de asistencias filtradas a la vista
        model.addAttribute("attendances", attendancesFiltradas);
        model.addAttribute("attendanceStatus", attendanceStatus);

        return "attendances/attendanceRegisterTeacher";
    }




    @GetMapping("/calls")
    public String listEventsForCalls(Model model) {
        AttendanceDto attendanceDto = new AttendanceDto();
        List<EventDto> events = eventService.findAll();
        List<UserDto> students = userService.getAllStudents();

        // Filtrar estudiantes que tengan al menos un evento pendiente de asistencia
        List<UserDto> filteredStudents = students.stream()
                .filter(student -> events.stream()
                        .anyMatch(event -> !attendanceService.hasAttendance(student.getId(), event.getId())))

                .collect(Collectors.toList());
        // Pasar la lista de reclamos a la vista
        model.addAttribute("events", events);
        model.addAttribute("students", filteredStudents);
        model.addAttribute("attendance", attendanceDto);

        return "callsStudentForEvent/callsStudentForEvent";
    }

    @PostMapping("/registerCall")
    public String registerCallAttendance(@ModelAttribute AttendanceDto attendanceDto) {
        // Lógica para guardar la asistencia
        attendanceService.saveCall(attendanceDto);
        return "redirect:/attendances/calls";
    }

    @PostMapping("/register")
    public String registerAttendance(@ModelAttribute AttendanceDto attendanceDto) {
        // Lógica para guardar la asistencia
        AttendanceDto attendanceOriginal = attendanceService.findById(attendanceDto.getId());
        attendanceDto.setStudent(attendanceOriginal.getStudent());
        attendanceDto.setEvent(attendanceOriginal.getEvent());
        attendanceService.save(attendanceDto);
        return "redirect:/attendances/attendancesAnalist";
    }

    @PostMapping("/registerByTeacher")
    public String registerAttendanceByTeacher(@ModelAttribute AttendanceDto attendanceDto) {
        // Lógica para guardar la asistencia
        attendanceService.save(attendanceDto);
        return "redirect:/attendances/attendancesTeacher";
    }


}
