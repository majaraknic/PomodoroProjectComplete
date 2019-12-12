package eu.execom.pomodoro.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InvalidUsernameException extends UsernameNotFoundException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
