package edu.utec.pinfraPft.dto;

import edu.utec.pinfraPft.model.Department;
import edu.utec.pinfraPft.model.Itr;
import edu.utec.pinfraPft.model.Locality;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDate;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min=3 , max = 20, message = "La contraseña no cumple los requisitos de tamaño, min 3, max 20")
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,}$", message = "La contraseña debe contener al menos una letra y un número")
    private String password;

    @NotEmpty(message = "First name cannot be empty")
    @Size(min=3 , max = 20, message = "El nombre no cumple los requisitos de tamaño, min 3, max 20")
    private String firstName;

    private String secondName;

    @Size(min=3 , max = 20, message = "El apellido no cumple los requisitos de tamaño, min 3, max 20")
    @NotEmpty(message = "First surname cannot be empty")
    private String firstSurname;

    @Size(min=3 , max = 20, message = "El apellido no cumple los requisitos de tamaño, min 3, max 20")
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

    @NotEmpty(message = "Gender cannot be empty")
    private String gender;

    //If the user is a student
    private int generation;

    //If the user is a teacher
    private String area;

    private String teacherRole;

    private boolean active;

    public UserDto(Long id, String username, String password, String firstName, String secondName, String firstSurname, String secondSurname, int document, LocalDate birthDate, String personalEmail, int phone, Department department, Locality locality, String institutionalEmail, Itr itr, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.document = document;
        this.birthDate = birthDate;
        this.personalEmail = personalEmail;
        this.phone = phone;
        this.department = department.getId();
        this.locality = locality.getId();
        this.institutionalEmail = institutionalEmail;
        this.itr = itr.getId();
        this.active = active;
    }
}
