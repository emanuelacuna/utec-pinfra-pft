package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDateTime created;
    private LocalDateTime updated;
    private String eventVme;
    private String activityApe;
    private int semester;
    private LocalDate date;
    private String teacher;
    private int credits;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
