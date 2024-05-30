package edu.utec.loginPinfra.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CareerDto {

    private Long id;

    @NotEmpty(message = "Career name should not be empty")
    @Size(min = 4, max = 48, message = "Career name should be between 4 and 48 characters")
    private String name;
}
