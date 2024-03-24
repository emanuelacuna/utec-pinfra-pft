package edu.utec.loginPinfra.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
/*TODO:
    -Modify the class to match the requirements, adding the student fields and the necessary constraints
*/
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

}
