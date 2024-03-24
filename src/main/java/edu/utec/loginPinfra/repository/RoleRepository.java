package edu.utec.loginPinfra.repository;

import edu.utec.loginPinfra.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
