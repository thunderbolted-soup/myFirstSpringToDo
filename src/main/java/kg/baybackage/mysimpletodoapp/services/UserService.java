package kg.baybackage.mysimpletodoapp.services;

import kg.baybackage.mysimpletodoapp.models.User;
import kg.baybackage.mysimpletodoapp.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // add, delete, update
    public User addUser(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new DataIntegrityViolationException("Email already exists");
        }
        if (repository.existsByUsername(user.getUsername())) {
            throw new DataIntegrityViolationException("Username already exists");
        }
        return repository.save(user);
    }



    public boolean deleteUserById(Long id) {
        repository.deleteById(id);
        return true;
    }

    public Optional<User> updateUser(Long id, User user) {
    return repository
            .findById(id)
            .map(existingUser -> {
            user.setId(id);
            return repository.save(user);
        });
}


    // getting some shit
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return repository.findById(id);
    }

    public User getUserByUsername(String username) {
        return repository.findByUsername(username);
    }

    // not using optional bc (nullable = false, unique = true) in user class
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }
}
