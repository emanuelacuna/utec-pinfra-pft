package edu.utec.pinfraPft.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class DepartmentDto {

    private Long id;

    private String name;
}
