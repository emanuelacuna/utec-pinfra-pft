package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByName(String name);

}
