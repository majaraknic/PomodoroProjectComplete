package eu.execom.pomodoro.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DataViolationException extends DataIntegrityViolationException {
    public DataViolationException(String message) {
        super(message);
    }
}
