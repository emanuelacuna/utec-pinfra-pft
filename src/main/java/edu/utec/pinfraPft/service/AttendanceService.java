package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.AttendanceDto;

public interface AttendanceService {

    public void save(AttendanceDto attendanceDto);
    public boolean hasAttendance(Long studentId, Long eventId);
}
