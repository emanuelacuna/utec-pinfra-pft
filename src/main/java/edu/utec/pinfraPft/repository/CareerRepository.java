package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CareerRepository extends JpaRepository<Career, Long> {

    Optional<Career> findByName(String name);

    @Query("SELECT c from Career c WHERE c.name LIKE CONCAT('%', :query, '%')")
    List<Career> filterCareer(String query);

}
