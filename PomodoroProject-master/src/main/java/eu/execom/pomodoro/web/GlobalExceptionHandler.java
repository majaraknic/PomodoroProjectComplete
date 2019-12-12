package eu.execom.pomodoro.web;

import eu.execom.pomodoro.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(DataViolationException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, DataViolationException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, InvalidUsernameException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameStringException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, SameStringException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NumberOfCharactersException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, NumberOfCharactersException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyInTeamException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, UserAlreadyInTeamException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidPasswordException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, NotValidPasswordException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoEntityException.class)
    public ResponseEntity<Map<String, String>> handle(HttpServletRequest request, NoEntityException ex) {

        return new ResponseEntity<>(Collections.singletonMap("error", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
