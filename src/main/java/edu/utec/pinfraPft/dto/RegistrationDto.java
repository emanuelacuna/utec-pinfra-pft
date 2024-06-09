package edu.utec.pinfraPft.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationDto {

    private Long id;

    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    private String surname;

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Birthdate should not be empty")
    private LocalDate birthDate;

    @NotEmpty(message = "Address should not be empty")
    private String address;

    private Long studentNumber;

    private Long career;

}
