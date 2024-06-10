package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.model.UserEntity;

public interface UserService {

    void register(UserDto userDto);
    UserEntity findByUsername(String username);
    UserEntity findByPersonalEmail(String personalEmail);
    UserEntity findByInstitutionalEmail(String institutionalEmail);
    UserEntity findByDocument(int document);

    boolean existsByUsername(String username);
    boolean existsByPersonalEmail(String personalEmail);
    boolean existsByInstitutionalEmail(String institutionalEmail);
    boolean existsByDocument(int document);

}
