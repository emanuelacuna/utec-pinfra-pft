package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Itr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItrRepository extends JpaRepository<Itr, Long> {
}
