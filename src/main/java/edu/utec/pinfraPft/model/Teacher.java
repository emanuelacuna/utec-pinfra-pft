package edu.utec.pinfraPft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Entity @PrimaryKeyJoinColumn(name = "id")
public class Teacher extends UserEntity {

    private String area;

    private String teacherRole;

    @ManyToMany(mappedBy = "teachers")
    private List<Event> events;

}
