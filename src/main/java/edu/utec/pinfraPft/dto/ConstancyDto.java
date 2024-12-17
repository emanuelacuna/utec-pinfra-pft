package edu.utec.pinfraPft.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConstancyDto {

    private Long id;

    private String constancyType;

    @NotEmpty(message = "Event cannot be empty")
    @NotNull(message = "Event cannot be null")
    private Long event;

    private String info;

    private LocalDateTime date;

    private Long student;

    private String status;

    private String eventTitle;

    private String studentName;
}
