package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Entity @PrimaryKeyJoinColumn(name = "id")
public class Student extends UserEntity {

    private int generation;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Justification> justifications;

}
