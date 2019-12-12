package eu.execom.pomodoro.exceptions;

public class InvalidLoginResponse {

    private String message;

    public InvalidLoginResponse() {
        this.message = "You are not authorized to access this resources";
    }
}
