package edu.utec.loginPinfra.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Entity @DiscriminatorValue(value = "student")
public class Student extends UserEntity {

    private Long studentNumber;

    @ManyToOne
    @JoinColumn(name = "career_id")
    private Career career;

}
