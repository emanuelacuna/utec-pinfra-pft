package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Claim;
import edu.utec.pinfraPft.model.Event;
import edu.utec.pinfraPft.model.Justification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {
}
