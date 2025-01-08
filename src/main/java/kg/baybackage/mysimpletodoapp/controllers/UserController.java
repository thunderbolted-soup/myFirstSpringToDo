package kg.baybackage.mysimpletodoapp.controllers;

import jakarta.validation.Valid;
import kg.baybackage.mysimpletodoapp.models.User;
import kg.baybackage.mysimpletodoapp.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("GET request to fetch all users");
        List<User> users = userService.getAllUsers();
        return users.isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("GET request to fetch user by id: {}", id);
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            log.error("Error fetching user with id {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<User> searchUser(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email) {
        log.info("GET request to search user by username: {} or email: {}", username, email);
        try {
            User user;
            if (username != null) {
                user = userService.getUserByUsername(username);
            } else if (email != null) {
                user = userService.getUserByEmail(email);
            } else {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            log.error("Error searching for user: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        log.info("POST request to create new user with username: {}", user.getUsername());
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (DataIntegrityViolationException e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {
        log.info("PUT request to update user with id: {}", id);
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (DataIntegrityViolationException e) {
            log.error("Conflict while updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("DELETE request to remove user with id: {}", id);
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}