package kg.baybackage.mysimpletodoapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kg.baybackage.mysimpletodoapp.enums.Priority;
import kg.baybackage.mysimpletodoapp.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks", schema = "public")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Priority is required")
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.UNDEFINED;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS;

    @NotNull(message = "Deadline is required")
    private LocalDate deadline;
}