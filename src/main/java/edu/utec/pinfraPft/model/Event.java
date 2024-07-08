package edu.utec.pinfraPft.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String eventType;

    private LocalDateTime startingDate;

    private LocalDateTime endingDate;

    private String mode;

    @ManyToOne
    @JoinColumn(name = "itr_id")
    private Itr itr;

    private String location;

    @ManyToMany
    @JoinTable(name = "event_teacher",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private List<Teacher> teachers;

    private String status;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

}
