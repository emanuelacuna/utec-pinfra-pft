package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    List<Claim> findAllByUserId(Long id);
}
