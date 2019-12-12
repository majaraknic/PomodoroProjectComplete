package eu.execom.pomodoro.service;

import eu.execom.pomodoro.exceptions.InvalidUsernameException;
import eu.execom.pomodoro.model.CustomUserDetails;
import eu.execom.pomodoro.model.User;
import eu.execom.pomodoro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws InvalidUsernameException {
        User user = userRepository.findByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        if (user == null) {
            throw new InvalidUsernameException("User not found");
        }

        return customUserDetails;
    }

    @Transactional
    public CustomUserDetails loadUserById(Long id) {
        User user = userRepository.getOne(id);

        if (user == null)
            new InvalidUsernameException("User not found");

        return new CustomUserDetails(user);
    }

}
