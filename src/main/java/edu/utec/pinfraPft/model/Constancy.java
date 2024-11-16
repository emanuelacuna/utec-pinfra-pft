package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Constancy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String constancyType;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    private String info;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private UserEntity student;

    private String status;

}
