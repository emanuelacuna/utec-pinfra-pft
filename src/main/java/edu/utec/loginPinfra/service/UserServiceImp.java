package edu.utec.loginPinfra.service;

import edu.utec.loginPinfra.dto.RegistrationDto;
import edu.utec.loginPinfra.dto.UpdateDto;
import edu.utec.loginPinfra.dto.UserDto;
import edu.utec.loginPinfra.model.Career;
import edu.utec.loginPinfra.model.Role;
import edu.utec.loginPinfra.model.Student;
import edu.utec.loginPinfra.model.UserEntity;
import edu.utec.loginPinfra.repository.CareerRepository;
import edu.utec.loginPinfra.repository.RoleRepository;
import edu.utec.loginPinfra.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final CareerRepository careerRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                          CareerRepository careerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.careerRepository = careerRepository;
    }

    @Override
    public void register(RegistrationDto registration){

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        Role studentRole = roleRepository.findByName("ROLE_STUDENT")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        UserEntity user;

        if (registration.getStudentNumber() != null &&
                registration.getCareer() != 0) {

            Career career = careerRepository.findById(registration.getCareer())
                    .orElseThrow(() -> new RuntimeException("Career not found"));

            user = Student.builder()
                    .username(registration.getUsername())
                    .password(passwordEncoder.encode(registration.getPassword()))
                    .name(registration.getName())
                    .surname(registration.getSurname())
                    .email(registration.getEmail())
                    .address(registration.getAddress())
                    .birthDate(registration.getBirthDate())
                    .role(Arrays.asList(studentRole))
                    .studentNumber(registration.getStudentNumber())
                    .career(career)
                    .active(false)
                    .build();
        } else {
            user = UserEntity.builder()
                    .username(registration.getUsername())
                    .password(passwordEncoder.encode(registration.getPassword()))
                    .name(registration.getName())
                    .surname(registration.getSurname())
                    .email(registration.getEmail())
                    .address(registration.getAddress())
                    .birthDate(registration.getBirthDate())
                    .role(Arrays.asList(userRole))
                    .active(false)
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
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserDto> findAll(){
        List<UserEntity> users = userRepository.findAll();
        users.remove(0);
        return users
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public void changeUserStatus(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(!user.isActive());
        userRepository.save(user);
    }

    public UserDto mapToDto(UserEntity user){
        if (user instanceof Student student) {
            return UserDto.builder()
                    .id(student.getId())
                    .name(student.getName())
                    .surname(student.getSurname())
                    .age(student.getAge())
                    .studentNumber(student.getStudentNumber())
                    .career(student.getCareer().getName())
                    .active(student.isActive())
                    .build();
        } else {
            return UserDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .age(user.getAge())
                    .active(user.isActive())
                    .build();
        }
    }

    public UserDto findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDto(user);
    }

    public UpdateDto getUserUpdateDto(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user instanceof Student student) {
            return UpdateDto.builder()
                    .id(student.getId())
                    .name(student.getName())
                    .surname(student.getSurname())
                    .studentNumber(student.getStudentNumber())
                    .career(student.getCareer().getId())
                    .build();
        } else {
            return UpdateDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .build();
        }
    }

    public void updateUser(UpdateDto updateDto) {
        UserEntity user = userRepository.findById(updateDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(updateDto.getName());
        user.setSurname(updateDto.getSurname());
        if (user instanceof Student student) {
            student.setStudentNumber(updateDto.getStudentNumber());
            student.setCareer(careerRepository.findById(updateDto.getCareer())
                    .orElseThrow(() -> new RuntimeException("Career not found")));
        }
        userRepository.save(user);
    }

}
