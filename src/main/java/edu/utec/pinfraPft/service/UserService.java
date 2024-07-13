package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

public interface UserService {

    void register(UserDto userDto);
    UserEntity findByUsername(String username);
    UserEntity findByPersonalEmail(String personalEmail);
    UserEntity findByInstitutionalEmail(String institutionalEmail);
    UserEntity findByDocument(int document);

    void updateUser(UserDto userDto);

    List<UserDto> findAll();

    void changeUserStatus(Long id);

    boolean existsByUsername(String username);
    boolean existsByPersonalEmail(String personalEmail);
    boolean existsByInstitutionalEmail(String institutionalEmail);
    boolean existsByDocument(int document);

    UserDto findUserDtoByUsername(String name);

    UserDto findUserDtoById(Long id);

    String getUsernameById(Long id);

    List<UserDto> findStudentsWithClaims();
}
