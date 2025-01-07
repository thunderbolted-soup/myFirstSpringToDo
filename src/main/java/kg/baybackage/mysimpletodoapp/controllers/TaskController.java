package kg.baybackage.mysimpletodoapp.controllers;

import kg.baybackage.mysimpletodoapp.models.Task;
import kg.baybackage.mysimpletodoapp.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// todo СДЕЛАТЬ КОД БОЛЕЕ БЕЗОПАСНЫМ!
@RestController
@RequestMapping("/api/v1/users/{userId}/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable Long userId) {
        List<Task> tasks = service.getAllTasks(userId);

        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/t{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long userId, @PathVariable Long id) {
        Optional<Task> task = service.getTaskById(id);

        if (task.isPresent() && !task.get().getUserId().equals(userId)){
            throw new AccessDeniedException("User does not have permission to this task");
        }

        return task.map(value ->
                new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity<Task> addTask(@PathVariable Long userId, @RequestBody Task task) {
        try{
            Task createdTask = service.createTask(task);
            return new ResponseEntity<>(task, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/t{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long userId, @PathVariable Long id, @RequestBody Task task) {
        try{
            Optional<Task> updatedTask = service.updateTask(id, task);
            if (updatedTask.isPresent()){
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
            return new ResponseEntity<>(task, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
