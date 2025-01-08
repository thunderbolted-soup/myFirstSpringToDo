package kg.baybackage.mysimpletodoapp.repository;

import kg.baybackage.mysimpletodoapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(@NonNull String username);

    User findByEmail(@NonNull String email);

    boolean existsByEmail(@NonNull String email);

    boolean existsByUsername(@NonNull String username);
}