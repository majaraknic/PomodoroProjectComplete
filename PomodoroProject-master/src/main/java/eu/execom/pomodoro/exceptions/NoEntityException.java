package eu.execom.pomodoro.exceptions;

import javax.persistence.EntityExistsException;

public class NoEntityException extends EntityExistsException {

    public NoEntityException(String message) {
        super(message);
    }

}
