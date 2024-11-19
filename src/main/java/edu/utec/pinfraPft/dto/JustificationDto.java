package edu.utec.pinfraPft.dto;

import edu.utec.pinfraPft.model.Student;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class JustificationDto {
    private Long id;
    private String event;
    private String information;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;

    private String status;
    private Long student;
}
