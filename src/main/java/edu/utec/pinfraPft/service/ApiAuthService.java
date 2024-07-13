package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.LoginDto;
import edu.utec.pinfraPft.dto.UserDto;
import edu.utec.pinfraPft.model.UserEntity;
import edu.utec.pinfraPft.security.CustomUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@RequiredArgsConstructor
public class ApiAuthService {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<AuthResponse> login(LoginDto loginDto) {

        UserEntity user = userService.findByUsername(loginDto.getUsername());


        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        else if(!user.isActive()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
            String token = jwtService.getToken(userDetails);
            AuthResponse authResponse = new AuthResponse(token, user);
            return ResponseEntity.ok(authResponse);
        }
    }

    @Setter
    @Getter
    public static class AuthResponse {
        // Getters y setters
        private String token;
        private UserDto usuarioDTO;

        public AuthResponse(String token, UserEntity usuario) {
            this.token = token;
            this.usuarioDTO = new UserDto(
                    usuario.getId(),
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getFirstName(),
                    usuario.getSecondName(),
                    usuario.getFirstSurname(),
                    usuario.getSecondSurname(),
                    usuario.getDocument(),
                    usuario.getBirthDate(),
                    usuario.getPersonalEmail(),
                    usuario.getPhone(),
                    usuario.getDepartment(),
                    usuario.getLocality(),
                    usuario.getInstitutionalEmail(),
                    usuario.getItr(),
                    usuario.isActive()
            );
        }

    }
}

