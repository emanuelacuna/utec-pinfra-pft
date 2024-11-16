package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.AttendanceDto;
import edu.utec.pinfraPft.model.Attendance;
import edu.utec.pinfraPft.repository.AttendanceRepository;
import edu.utec.pinfraPft.repository.EventRepository;
import edu.utec.pinfraPft.repository.UserRepository;
import edu.utec.pinfraPft.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    // Método para convertir un AttendanceDto a una entidad Attendance
    private Attendance mapToEntity(AttendanceDto attendanceDto) {
        Attendance attendance = new Attendance();
        attendance.setId(attendanceDto.getId());
        attendance.setStudent(userRepository.findById(attendanceDto.getStudent())
                .orElseThrow(() -> new RuntimeException("User not found"))); // Asegúrate de que el Dto contenga el objeto Student
        attendance.setEvent(eventRepository.findById(attendanceDto.getEvent())
                .orElseThrow(() -> new RuntimeException("Event not found")));
        attendance.setStatus(attendanceDto.getStatus());
        attendance.setQualification(attendanceDto.getQualification());
        return attendance;
    }

    // Método para convertir una entidad Attendance a un AttendanceDto
    private AttendanceDto mapToDto(Attendance attendance) {
        AttendanceDto attendanceDto = new AttendanceDto();
        attendanceDto.setId(attendance.getId());
        attendanceDto.setStudent(attendance.getStudent().getId());
        attendanceDto.setEvent(attendance.getEvent().getId());
        attendanceDto.setStatus(attendance.getStatus());
        attendanceDto.setQualification(attendance.getQualification());
        return attendanceDto;
    }

    @Override
    public void save(AttendanceDto attendanceDto) {
        // Mapea el AttendanceDto a una entidad y guarda en la base de datos
        Attendance attendance = mapToEntity(attendanceDto);
        attendanceRepository.save(attendance);
    }

    @Override
    public boolean hasAttendance(Long studentId, Long eventId) {
        return attendanceRepository.existsByStudentIdAndEventId(studentId , eventId);
    }
}
