package kg.baybackage.mysimpletodoapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true) // оно не может быть пустым и должно быть уникальным
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
