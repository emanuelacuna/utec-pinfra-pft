package edu.utec.pinfraPft.dto;

import lombok.Data;

@Data
public class AttendanceDto {

    private Long id;
    private Long event;
    private Long student;
    private String status;
    private int qualification;

}
