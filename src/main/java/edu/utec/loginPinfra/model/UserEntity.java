package edu.utec.loginPinfra.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
/*TODO:
    -Modify user to match the requirements in the project
    -Add subclass Student
    -Add Career and a One to Many relationship to Student
*/
public class UserEntity{

    @Id @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private String surname;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> role = new ArrayList<>();
}
