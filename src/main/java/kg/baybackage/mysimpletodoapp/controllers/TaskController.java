package kg.baybackage.mysimpletodoapp.controllers;

import kg.baybackage.mysimpletodoapp.enums.Priority;
import kg.baybackage.mysimpletodoapp.enums.Status;
import kg.baybackage.mysimpletodoapp.models.Task;
import kg.baybackage.mysimpletodoapp.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// todo СДЕЛАТЬ КОД БОЛЕЕ БЕЗОПАСНЫМ! я добавил в параметры многих методов userId, чтобы реализовать верификацию ASAP
@RestController
@RequestMapping("/api/v1/users/{userId}")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/all")
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
    @GetMapping("/findTitle")
    public ResponseEntity<List<Task>> getTasksContainsTitle(@PathVariable Long userId, @RequestParam String title){
        try {
            List<Task> tasks = service.getTasksContainTitle(title, userId);
            if (tasks.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/findStatus")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable Long userId, @RequestParam Status status){
        try{
            List<Task> tasks = service.getByStatus(status, userId);
            if (tasks.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findStatusNot")
    public ResponseEntity<List<Task>> getTasksByStatusNot(@PathVariable Long userId, @RequestParam Status status){
        try{
            List<Task> tasks = service.getByStatusNot(status, userId);
            if (tasks.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findPriority")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Long userId, @RequestParam Priority priority){
        try{
            List<Task> tasks = service.getByPriority(priority, userId);
            if (tasks.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@PathVariable Long userId, @RequestBody Task task) {
        try{
            // todo! add user verification via spring security
            Task createdTask = service.createTask(task);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/t{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long userId, @PathVariable Long id, @RequestBody Task task) {
        try{
            Optional<Task> updatedTask = service.updateTask(id, task);
            if (updatedTask.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/t{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id, @PathVariable Long userId){
        try {
            Optional<Task> deletedTask = service.deleteTask(id, userId);
            if (deletedTask.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
