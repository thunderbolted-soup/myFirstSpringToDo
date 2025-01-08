package kg.baybackage.mysimpletodoapp.controllers;

import jakarta.validation.Valid;
import kg.baybackage.mysimpletodoapp.models.Task;
import kg.baybackage.mysimpletodoapp.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/{userId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable Long userId) {
        List<Task> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long userId, @PathVariable Long id) {
        Task task = taskService.getTaskById(id, userId);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@PathVariable Long userId, @RequestParam String title) {
        List<Task> tasks = taskService.getTasksContainTitle(title, userId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@PathVariable Long userId, @Valid @RequestBody Task task) {
        Task createdTask = taskService.createTask(task, userId);
        return ResponseEntity.ok(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long userId,
                                         @PathVariable Long id,
                                         @Valid @RequestBody Task task) {
        Task updatedTask = taskService.updateTask(id, task, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long userId, @PathVariable Long id) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.ok().build();
    }
}