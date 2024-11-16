package edu.utec.pinfraPft.repository;

import edu.utec.pinfraPft.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByPersonalEmail(String personalEmail);
    Optional<UserEntity> findByInstitutionalEmail(String institutionalEmail);
    Optional<UserEntity> findByDocument(int document);
    Optional<UserEntity> findUserById(Long id);

    boolean existsByUsername(String username);
    boolean existsByPersonalEmail(String personalEmail);
    boolean existsByInstitutionalEmail(String institutionalEmail);
    boolean existsByDocument(int document);

    @Query("SELECT u FROM UserEntity u JOIN u.role r WHERE r.id = :roleId")
    List<UserEntity> findUsersByRoleId(@Param("roleId") Long roleId);
}
