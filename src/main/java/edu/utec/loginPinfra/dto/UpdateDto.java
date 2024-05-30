package edu.utec.loginPinfra.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UpdateDto {

    private Long id;

    private String username;

    private String name;

    private String surname;

    //If user is a student
    private Long studentNumber;

    private Long career;

}
