package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}
