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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                    .role(Collections.singletonList(studentRole))
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
                    .teacherRole(registration.getTeacherRole())
                    .active(false)
                    .role(Collections.singletonList(teacherRole))
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
    public UserDto findUserDtoById(Long id) {
        return mapToDto(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
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

    @Override
    public void updateUser(UserDto userDto) {

        UserEntity user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        user.setFirstSurname(userDto.getFirstSurname());
        user.setSecondSurname(userDto.getSecondSurname());
        user.setDocument(userDto.getDocument());
        user.setBirthDate(userDto.getBirthDate());
        user.setPersonalEmail(userDto.getPersonalEmail());
        user.setPhone(userDto.getPhone());

        user.setDepartment(departmentRepository.findById(userDto.getDepartment())
                .orElseThrow(() -> new RuntimeException("Department not found")));

        user.setLocality(localityRepository.findById(userDto.getLocality())
                .orElseThrow(() -> new RuntimeException("Locality not found")));

        user.setInstitutionalEmail(userDto.getInstitutionalEmail());

        user.setItr(itrRepository.findById(userDto.getItr())
                .orElseThrow(() -> new RuntimeException("Itr not found")));

        if (user instanceof Student student) {
            student.setGeneration(userDto.getGeneration());
        } else if (user instanceof Teacher teacher) {
            teacher.setArea(userDto.getArea());
            teacher.setTeacherRole(userDto.getTeacherRole());
        }

        user.setActive(userDto.isActive());

        userRepository.save(user);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findUserDtoByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return mapToDto(user);
    }

    public UserDto mapToDto(UserEntity user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .firstSurname(user.getFirstSurname())
                .secondSurname(user.getSecondSurname())
                .document(user.getDocument())
                .birthDate(user.getBirthDate())
                .personalEmail(user.getPersonalEmail())
                .phone(user.getPhone())
                .department(user.getDepartment().getId())
                .locality(user.getLocality().getId())
                .institutionalEmail(user.getInstitutionalEmail())
                .itr(user.getItr().getId())
                .active(user.isActive())
                .build();
        if (user instanceof Student student) {
            userDto.setGeneration(student.getGeneration());
        } else if (user instanceof Teacher teacher) {
            userDto.setArea(teacher.getArea());
            userDto.setTeacherRole(teacher.getTeacherRole());
        }
        return userDto;
    }

    public UserEntity mapToEntity(UserDto userDto) {

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Role teacherRole = roleRepository.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserEntity user;

        if (userDto.getGeneration() != 0) {
            user = Student.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .firstName(userDto.getFirstName())
                    .secondName(userDto.getSecondName())
                    .firstSurname(userDto.getFirstSurname())
                    .secondSurname(userDto.getSecondSurname())
                    .document(userDto.getDocument())
                    .birthDate(userDto.getBirthDate())
                    .personalEmail(userDto.getPersonalEmail())
                    .phone(userDto.getPhone())
                    .department(departmentRepository.findById(userDto.getDepartment())
                            .orElseThrow(() -> new RuntimeException("Department not found")))
                    .locality(localityRepository.findById(userDto.getLocality())
                            .orElseThrow(() -> new RuntimeException("Locality not found")))
                    .institutionalEmail(userDto.getInstitutionalEmail())
                    .itr(itrRepository.findById(userDto.getItr())
                            .orElseThrow(() -> new RuntimeException("Itr not found")))
                    .generation(userDto.getGeneration())
                    .active(userDto.isActive())
                    .role(Arrays.asList(studentRole))
                    .build();
        } else {
            user = Teacher.builder()
                    .id(userDto.getId())
                    .username(userDto.getUsername())
                    .password(userDto.getPassword())
                    .firstName(userDto.getFirstName())
                    .secondName(userDto.getSecondName())
                    .firstSurname(userDto.getFirstSurname())
                    .secondSurname(userDto.getSecondSurname())
                    .document(userDto.getDocument())
                    .birthDate(userDto.getBirthDate())
                    .personalEmail(userDto.getPersonalEmail())
                    .phone(userDto.getPhone())
                    .department(departmentRepository.findById(userDto.getDepartment())
                            .orElseThrow(() -> new RuntimeException("Department not found")))
                    .locality(localityRepository.findById(userDto.getLocality())
                            .orElseThrow(() -> new RuntimeException("Locality not found")))
                    .institutionalEmail(userDto.getInstitutionalEmail())
                    .itr(itrRepository.findById(userDto.getItr())
                            .orElseThrow(() -> new RuntimeException("Itr not found")))
                    .area(userDto.getArea())
                    .teacherRole(userDto.getTeacherRole())
                    .active(userDto.isActive())
                    .role(Arrays.asList(teacherRole))
                    .build();
        }
        return user;
    }

    @Override
    public String getUsernameById(Long id) {
        return userRepository
                .findById(id)
                .map(UserEntity::getUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
