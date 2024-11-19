package edu.utec.pinfraPft.repository;


import edu.utec.pinfraPft.model.Constancy;
import edu.utec.pinfraPft.model.Justification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConstancyRepository extends JpaRepository<Constancy, Long> {
    List<Constancy> findConstancyByStudent_Id(Long studentId);
    List<Constancy> findAllByStudent_Id(Long id);
}
