package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Itr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "itr", cascade = CascadeType.ALL)
    private List<UserEntity> users;

    @OneToMany(mappedBy = "itr", cascade = CascadeType.ALL)
    private List<Event> events;
}
