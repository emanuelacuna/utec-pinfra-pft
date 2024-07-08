package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
