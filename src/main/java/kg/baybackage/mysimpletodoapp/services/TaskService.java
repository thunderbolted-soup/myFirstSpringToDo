package kg.baybackage.mysimpletodoapp.services;

import jakarta.persistence.EntityNotFoundException;
import kg.baybackage.mysimpletodoapp.enums.Priority;
import kg.baybackage.mysimpletodoapp.enums.Status;
import kg.baybackage.mysimpletodoapp.models.Task;
import kg.baybackage.mysimpletodoapp.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks(Long userId) {
        validateUserId(userId);
        return taskRepository.findByUserId(userId);
    }

    public Task getTaskById(Long id, Long userId) {
        validateId(id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        validateTaskOwnership(task, userId);
        return task;
    }

    public List<Task> getTasksContainTitle(String title, Long userId) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        validateUserId(userId);
        return taskRepository.findByTitleContaining(title.trim(), userId);
    }

    public Task createTask(Task task, Long userId) {
        validateTask(task);
        validateTaskOwnership(task, userId);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task updatedTask, Long userId) {
        validateId(id);
        validateTask(updatedTask);

        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        validateTaskOwnership(existingTask, userId);

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDeadline(updatedTask.getDeadline());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id, Long userId) {
        validateId(id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));
        validateTaskOwnership(task, userId);
        taskRepository.deleteById(id);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
    }

    private void validateUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private void validateTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
    }

    private void validateTaskOwnership(Task task, Long userId) {
        if (!task.getUserId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to access this task");
        }
    }
}