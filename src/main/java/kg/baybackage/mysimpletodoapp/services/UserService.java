package kg.baybackage.mysimpletodoapp.services;

import jakarta.persistence.EntityNotFoundException;
import kg.baybackage.mysimpletodoapp.models.User;
import kg.baybackage.mysimpletodoapp.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    public User createUser(User user) {
        validateNewUser(user);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);

        if (!existingUser.getEmail().equals(user.getEmail()) &&
            userRepository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        if (!existingUser.getUsername().equals(user.getUsername()) &&
            userRepository.existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private void validateNewUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }
    }
}