package eu.execom.pomodoro.service;

import eu.execom.pomodoro.exceptions.DataViolationException;
import eu.execom.pomodoro.exceptions.NoEntityException;
import eu.execom.pomodoro.exceptions.NumberOfCharactersException;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) { return userRepository.getOne(id); }

//    public List<User> getByTeamId(Long teamId) { return (List<User>) userRepository.getByTeamId(teamId); }

//    public User deleteByTeamId(Long teamId) { return userRepository.deleteByTeamId(teamId); }

    public User getByEmail(String email) {
        User user =userRepository.getByEmail(email);
        if (user == null) {
            throw new NoEntityException("User with this id doesn't exist.");
        }
        return user;
    }

    public User save(User user) {
        validatePasswordLength(user);
        validateEmailExistence(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    private void validateEmailExistence(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DataViolationException("User with this email already exists.");
        }
    }

    private void validatePasswordLength(User user) {
        if (user.getPassword().length() < 5) {
            throw new NumberOfCharactersException("Password must have at least 5 characters!");
        }
    }

    public void delete(Long id) {
        checkIfUserExists(id);

        userRepository.deleteById(id);
    }

    private void checkIfUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoEntityException("User with this id doesn't exist in database.");
        }
    }
}
