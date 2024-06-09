package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.RegistrationDto;
import edu.utec.pinfraPft.model.UserEntity;

public interface UserService {

    void register(RegistrationDto registrationDto);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
