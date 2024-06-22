package edu.utec.pinfraPft.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClaimDto {

    private Long id;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private LocalDateTime created;

    private LocalDateTime updated;

    private String eventVme;

    private String activityApe;

    private int semester;

    private LocalDate date;

    private String teacher;

    private int credits;

    private String status;

    private Long user;

}
