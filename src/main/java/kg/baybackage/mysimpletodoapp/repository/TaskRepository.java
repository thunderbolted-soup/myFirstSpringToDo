package kg.baybackage.mysimpletodoapp.repository;

import kg.baybackage.mysimpletodoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @NonNull
    List<Task> findByUserId(@NonNull Long userId);

    @NonNull
    List<Task> findByTitleContaining(@NonNull String title, @NonNull Long userId);

    @NonNull
    Optional<Task> findById(@NonNull Long id);
}