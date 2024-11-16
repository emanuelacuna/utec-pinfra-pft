package edu.utec.pinfraPft.dto;

import lombok.Data;

@Data
public class ActionTakenDto {
    private Long id;
    private Long claim;
    private Long admin;
    private String status;
    private String actionTaken;
}
