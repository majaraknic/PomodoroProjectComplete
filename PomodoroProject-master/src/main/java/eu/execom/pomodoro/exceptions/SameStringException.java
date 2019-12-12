package eu.execom.pomodoro.exceptions;

public class SameStringException extends IllegalArgumentException {

    public SameStringException(String message) {
        super(message);
    }

}
