package kg.baybackage.mysimpletodoapp.repository;

import kg.baybackage.mysimpletodoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByTitleContaining(String title, Long userId);
    Optional<Task> findById(Long id);
}