package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByPersonalEmail(String personalEmail);
    Optional<UserEntity> findByInstitutionalEmail(String institutionalEmail);
    Optional<UserEntity> findByDocument(int document);

    boolean existsByUsername(String username);
    boolean existsByPersonalEmail(String personalEmail);
    boolean existsByInstitutionalEmail(String institutionalEmail);
    boolean existsByDocument(int document);
}
