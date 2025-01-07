package kg.baybackage.mysimpletodoapp.services;

import jakarta.persistence.EntityNotFoundException;
import kg.baybackage.mysimpletodoapp.enums.Priority;
import kg.baybackage.mysimpletodoapp.enums.Status;
import kg.baybackage.mysimpletodoapp.models.Task;
import kg.baybackage.mysimpletodoapp.repository.TaskRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> getAllTasks(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return taskRepository.findByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(Task::getDeadline).reversed())
                .collect(Collectors.toList());
    }


    public Optional<Task> getTaskById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Task ID cannot be null");
        }
        return taskRepository.findById(id);
    }


    public List<Task> getTasksContainTitle(String title, Long userId) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return taskRepository.findByTitleContaining(title, userId);
    }


    public List<Task> getByStatus(Status status, Long userId) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return taskRepository.findByStatus(status, userId);
    }


    public List<Task> getByStatusNot(Status status, Long userId) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return taskRepository.findByStatusNot(status, userId);
    }


    public List<Task> getByPriority(Priority priority, Long userId) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        return taskRepository.findByPriority(priority, userId);
    }


    public Task createTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        taskRepository.save(task);
        return task;
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {

            if (updatedTask.getTitle() != null && !updatedTask.getTitle().equals(task.getTitle())) {
                task.setTitle(updatedTask.getTitle());
            }
            if (updatedTask.getDescription() != null && !updatedTask.getDescription().equals(task.getDescription())) {
                task.setDescription(updatedTask.getDescription());
            }
            if (updatedTask.getPriority() != null && !updatedTask.getPriority().equals(task.getPriority())) {
                task.setPriority(updatedTask.getPriority());
            }
            if (updatedTask.getStatus() != null && !updatedTask.getStatus().equals(task.getStatus())) {
                task.setStatus(updatedTask.getStatus());
            }
            if (updatedTask.getDeadline() != null && !updatedTask.getDeadline().equals(task.getDeadline())) {
                task.setDeadline(updatedTask.getDeadline());
            }

            return taskRepository.save(task);
        });
    }

    public void deleteTask(Long id, Long userId){
        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Task not found."));

        if (!task.getUserId().equals(userId)){
            throw new AccessDeniedException("User does not have permission to delete this task");
        }
        taskRepository.deleteById(id);
    }

}
