package edu.utec.pinfraPft.service;

import edu.utec.pinfraPft.dto.LoginDto;
import edu.utec.pinfraPft.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiAuthService {

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public String login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getUsername());
        return jwtService.getToken(userDetails);
    }

}
