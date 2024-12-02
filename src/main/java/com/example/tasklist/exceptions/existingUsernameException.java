package com.example.tasklist.exceptions;

public class existingUsernameException extends RuntimeException {
    public existingUsernameException() {super("username already exists");}
    public existingUsernameException(String message) {super(message);}
}
