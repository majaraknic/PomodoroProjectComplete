package eu.execom.pomodoro.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class NumberOfCharactersException extends DataIntegrityViolationException {

    public NumberOfCharactersException(String message) {
        super(message);
    }

}
