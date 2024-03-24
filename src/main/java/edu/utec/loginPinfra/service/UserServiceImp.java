package edu.utec.loginPinfra.service;

import edu.utec.loginPinfra.dto.RegistrationDto;
import edu.utec.loginPinfra.model.Role;
import edu.utec.loginPinfra.model.UserEntity;
import edu.utec.loginPinfra.repository.RoleRepository;
import edu.utec.loginPinfra.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service

/*TODO:
    -Modify user to match the requirements in the project
*/
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegistrationDto registration){

        Role role = roleRepository.findByName("ROLE_USER");

        UserEntity user = UserEntity.builder()
                .username(registration.getUsername())
                .password(passwordEncoder.encode(registration.getPassword()))
                .name(registration.getName())
                .surname(registration.getSurname())
                .email(registration.getEmail())
                .role(Arrays.asList(role))
                .build();
        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
