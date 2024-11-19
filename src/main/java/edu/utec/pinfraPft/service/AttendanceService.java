package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.AttendanceDto;

import java.util.List;

public interface AttendanceService {

     void save(AttendanceDto attendanceDto);
     void saveCall(AttendanceDto attendanceDto);
     List<AttendanceDto> findAll();
     AttendanceDto findById(long id);
     boolean hasAttendance(Long studentId, Long eventId);
}
