package kg.baybackage.mysimpletodoapp.repository;

import kg.baybackage.mysimpletodoapp.enums.Status;
import kg.baybackage.mysimpletodoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Найти задачи с определенным статусом, пригодится чтобы найти невыполненные задачи
    List<Task> findByStatus(Status status);

    // Найти юзера по ID
    List<Task> findByUserId(Long userId);

}