package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Locality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalityRepository extends JpaRepository<Locality, Long> {
    List<Locality> findByDepartmentId(Long departmentId);
}
