package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Justification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JustificationRepository extends JpaRepository<Justification, Long> {
    List<Justification> findAllByStudent_Id(Long id);
}
