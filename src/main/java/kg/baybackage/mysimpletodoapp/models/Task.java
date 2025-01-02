package kg.baybackage.mysimpletodoapp.models;

import jakarta.persistence.*;
import kg.baybackage.mysimpletodoapp.enums.Priority;
import kg.baybackage.mysimpletodoapp.enums.Status;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // just ID

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String title; // just title

    @Column(nullable = false)
    private String description; // just description

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.UNDEFINED; // based on enums (LOW, MEDIUM, HIGH, UNDEFINED)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS; // based on enums (TОDО, IN_PROGRESS, DONE)

    @Column(nullable = false)
    private LocalDate deadline; // LocalDate
}
