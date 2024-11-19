package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Attendance;
import edu.utec.pinfraPft.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Boolean existsByStudentIdAndEventId(Long studentId,Long eventId);

    @Query("SELECT e FROM Event e JOIN e.attendances a WHERE a.student.id = :studentId")
    List<Event> findEventsByStudentId(@Param("studentId") Long studentId);
}
