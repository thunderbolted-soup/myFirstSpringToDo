package kg.baybackage.mysimpletodoapp.repository;

import kg.baybackage.mysimpletodoapp.enums.Priority;
import kg.baybackage.mysimpletodoapp.enums.Status;
import kg.baybackage.mysimpletodoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // find tasks that done, undone or smth
    List<Task> findByStatus(Status status, Long userId);

    // find all tasks of user
    List<Task> findByUserId(Long userId);

    List<Task> findByStatusNot(Status status, Long userId);

    // find by title
    List<Task> findByTitleContaining(String title, Long userId);

    List<Task> findByPriority(Priority priority, Long userId);
}
