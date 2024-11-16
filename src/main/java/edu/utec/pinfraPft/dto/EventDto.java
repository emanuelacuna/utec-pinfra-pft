package edu.utec.pinfraPft.dto;

import edu.utec.pinfraPft.model.Itr;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDto {

    private Long id;

    private String title;

    private String eventType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endingDate;

    private String mode;

    private Long itr;

    private String location;

    private List<Long> teachers;

    private String status;

    private String teacherNames;

    private String itrName;

}
