package edu.utec.pinfraPft.service.imp;

import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.model.*;
import edu.utec.pinfraPft.repository.*;

import edu.utec.pinfraPft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final DepartmentRepository departmentRepository;

    private final LocalityRepository localityRepository;

    private final ItrRepository itrRepository;

    @Override
    public void register(UserDto registration){

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Role teacherRole = roleRepository.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Department department = departmentRepository.findById(registration.getDepartment())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Locality locality = localityRepository.findById(registration.getLocality())
                .orElseThrow(() -> new RuntimeException("Locality not found"));

        Itr itr = itrRepository.findById(registration.getItr())
                .orElseThrow(() -> new RuntimeException("Itr not found"));

        UserEntity user;

        if(registration.getGeneration() != 0) {
            user = Student.builder()
                    .username(registration.getUsername())
                    .password(passwordEncoder.encode(registration.getPassword()))
                    .firstName(registration.getFirstName())
                    .secondName(registration.getSecondName())
                    .firstSurname(registration.getFirstSurname())
                    .secondSurname(registration.getSecondSurname())
                    .document(registration.getDocument())
                    .birthDate(registration.getBirthDate())
                    .personalEmail(registration.getPersonalEmail())
                    .phone(registration.getPhone())
                    .department(department)
                    .locality(locality)
                    .institutionalEmail(registration.getInstitutionalEmail())
                    .itr(itr)
                    .generation(registration.getGeneration())
                    .active(false)
                    .role(Arrays.asList(studentRole))
                    .build();
        } else {
            user = Teacher.builder()
                    .username(registration.getUsername())
                    .password(passwordEncoder.encode(registration.getPassword()))
                    .firstName(registration.getFirstName())
                    .secondName(registration.getSecondName())
                    .firstSurname(registration.getFirstSurname())
                    .secondSurname(registration.getSecondSurname())
                    .document(registration.getDocument())
                    .birthDate(registration.getBirthDate())
                    .personalEmail(registration.getPersonalEmail())
                    .phone(registration.getPhone())
                    .department(department)
                    .locality(locality)
                    .institutionalEmail(registration.getInstitutionalEmail())
                    .itr(itr)
                    .area(registration.getArea())
                    .tutorRole(registration.getTutorRole())
                    .active(false)
                    .role(Arrays.asList(teacherRole))
                    .build();
        }

        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserEntity findByPersonalEmail(String personalEmail) {
        return userRepository.findByPersonalEmail(personalEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    @Override
    public UserEntity findByInstitutionalEmail(String institutionalEmail) {
        return userRepository.findByInstitutionalEmail(institutionalEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    @Override
    public UserEntity findByDocument(int document) {
        return userRepository.findByDocument(document)
                .orElseThrow(() -> new UsernameNotFoundException("Document not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByPersonalEmail(String personalEmail) {
        return userRepository.existsByPersonalEmail(personalEmail);
    }

    @Override
    public boolean existsByInstitutionalEmail(String institutionalEmail) {
        return userRepository.existsByInstitutionalEmail(institutionalEmail);
    }

    @Override
    public boolean existsByDocument(int document) {
        return userRepository.existsByDocument(document);
    }

    public void changeUserStatus(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }
}
