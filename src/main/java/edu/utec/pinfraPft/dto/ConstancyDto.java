package edu.utec.pinfraPft.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConstancyDto {

    private Long id;

    private String constancyType;

    private Long event;

    private String info;

    private LocalDateTime date;

    private Long student;

    private String status;

    private String eventTitle;

    private String studentName;
}
