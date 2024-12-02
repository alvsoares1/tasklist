package com.example.tasklist.exceptions;

public class wrongPasswordException extends RuntimeException {
    public wrongPasswordException() {super("incorrect password");}
    public wrongPasswordException(String message) {super(message);}
}
