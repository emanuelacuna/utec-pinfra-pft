package edu.utec.loginPinfra.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
@Entity
public class Role {

    @Id @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "role")
    @ToString.Exclude
    private List<UserEntity> users = new ArrayList<>();

}
