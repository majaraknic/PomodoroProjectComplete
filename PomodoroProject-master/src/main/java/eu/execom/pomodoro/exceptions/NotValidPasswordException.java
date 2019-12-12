package eu.execom.pomodoro.exceptions;

public class NotValidPasswordException extends IllegalArgumentException {

    public NotValidPasswordException(String message) {
            super(message);
    }

}
