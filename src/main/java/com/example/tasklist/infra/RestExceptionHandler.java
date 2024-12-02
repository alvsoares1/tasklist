package com.example.tasklist.infra;

import com.example.tasklist.exceptions.existingEmailException;
import com.example.tasklist.exceptions.existingUsernameException;
import com.example.tasklist.exceptions.userNotFoundException;
import com.example.tasklist.exceptions.wrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(existingUsernameException.class)
    public ResponseEntity<Object> handleExistingUsernameException(existingUsernameException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler(existingEmailException.class)
    public ResponseEntity<Object> handleExistingEmailException(existingEmailException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler(userNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(userNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @ExceptionHandler(wrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException(wrongPasswordException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }
}
