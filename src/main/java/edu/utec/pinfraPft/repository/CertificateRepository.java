package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
