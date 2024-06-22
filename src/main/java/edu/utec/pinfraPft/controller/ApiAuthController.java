package edu.utec.pinfraPft.controller;

import edu.utec.pinfraPft.dto.LoginDto;
import edu.utec.pinfraPft.service.ApiAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ApiAuthController {

    private final ApiAuthService apiAuthService;


    @RequestMapping("/")
    public String login(@RequestBody LoginDto loginDto) {
        return apiAuthService.login(loginDto);
    }

}
