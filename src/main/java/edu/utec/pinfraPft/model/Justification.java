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
public class Justification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String event;

    private String information;

    private LocalDateTime date;

    private String status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
