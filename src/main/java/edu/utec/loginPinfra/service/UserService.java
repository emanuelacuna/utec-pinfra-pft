package edu.utec.loginPinfra.service;

import edu.utec.loginPinfra.dto.RegistrationDto;
import edu.utec.loginPinfra.model.UserEntity;

public interface UserService {

    void register(RegistrationDto registrationDto);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
