package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ToString.Exclude
    @OneToMany(mappedBy = "career")
    private List<Student> students;

    public Career(String name) {
        this.name = name;
    }
}
