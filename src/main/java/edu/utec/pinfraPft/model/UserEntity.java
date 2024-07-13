package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity{

    @Id @GeneratedValue(strategy =
            GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String secondName;

    private String firstSurname;

    private String secondSurname;

    private int document;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String personalEmail;

    private int phone;

    private String gender;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    private String institutionalEmail;

    @ManyToOne
    @JoinColumn(name = "itr_id")
    private Itr itr;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Claim> claims;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> role = new ArrayList<>();

    private boolean active;

    public int getAge() {
        return LocalDate.now().getYear() - getBirthDate().getYear();
    }
}
