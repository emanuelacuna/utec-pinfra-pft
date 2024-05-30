package edu.utec.loginPinfra.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private int age;

    private Long studentNumber;

    private String career;

    private boolean active;



}
