package edu.utec.pinfraPft.dto;

import edu.utec.pinfraPft.model.Department;
import edu.utec.pinfraPft.model.Itr;
import edu.utec.pinfraPft.model.Locality;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    private String secondName;

    @NotEmpty(message = "First surname cannot be empty")
    private String firstSurname;

    @NotEmpty(message = "Second surname cannot be empty")
    private String secondSurname;

    @NotNull(message = "Document cannot be null")
    private int document;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotEmpty(message = "Personal email cannot be empty")
    private String personalEmail;

    @NotNull(message = "Phone cannot be null")
    private int phone;

    @NotNull(message = "Department cannot be null")
    private Long department;

    @NotNull(message = "Locality cannot be null")
    private Long locality;

    @NotEmpty(message = "Institutional email cannot be empty")
    private String institutionalEmail;

    @NotNull(message = "Itr cannot be null")
    private Long itr;

    //If the user is a student
    private int generation;

    //If the user is a teacher
    private String area;

    private String teacherRole;

    private boolean active;

}
