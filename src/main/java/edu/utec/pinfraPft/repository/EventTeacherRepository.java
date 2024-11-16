package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTeacherRepository extends JpaRepository<Event, Long> {
    List<Event> findByTeachers_Id(Long teacherId);
}
