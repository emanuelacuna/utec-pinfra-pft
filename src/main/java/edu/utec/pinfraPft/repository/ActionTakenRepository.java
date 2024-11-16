package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.ActionTaken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTakenRepository extends JpaRepository<ActionTaken, Long> {
}
