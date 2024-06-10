package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Entity @PrimaryKeyJoinColumn(name = "id")
public class Student extends UserEntity {

    private int generation;

}
