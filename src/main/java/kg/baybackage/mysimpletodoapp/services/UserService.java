package kg.baybackage.mysimpletodoapp.services;

import jakarta.persistence.EntityNotFoundException;
import kg.baybackage.mysimpletodoapp.models.User;
import kg.baybackage.mysimpletodoapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        log.info("Fetching user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new EntityNotFoundException("User not found with id: " + id);
                });
    }

    public User getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found with username: {}", username);
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            log.error("User not found with email: {}", email);
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User createUser(User user) {
        log.info("Creating new user with username: {}", user.getUsername());
        validateNewUser(user);
        User savedUser = userRepository.save(user);
        log.info("Successfully created user with id: {}", savedUser.getId());
        return savedUser;
    }

    public User updateUser(Long id, User user) {
        log.info("Updating user with id: {}", id);
        User existingUser = getUserById(id);

        if (!existingUser.getEmail().equals(user.getEmail()) &&
            userRepository.existsByEmail(user.getEmail())) {
            log.error("Email already exists: {}", user.getEmail());
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (!existingUser.getUsername().equals(user.getUsername()) &&
            userRepository.existsByUsername(user.getUsername())) {
            log.error("Username already exists: {}", user.getUsername());
            throw new DataIntegrityViolationException("Username already exists");
        }

        user.setId(id);
        User updatedUser = userRepository.save(user);
        log.info("Successfully updated user with id: {}", id);
        return updatedUser;
    }

    public void deleteUser(Long id) {
        log.info("Attempting to delete user with id: {}", id);
        if (!userRepository.existsById(id)) {
            log.error("User not found with id: {}", id);
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("Successfully deleted user with id: {}", id);
    }

    // Валидатчик)
    private void validateNewUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            log.error("Email already exists: {}", user.getEmail());
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            log.error("Username already exists: {}", user.getUsername());
            throw new DataIntegrityViolationException("Username already exists");
        }
    }
}