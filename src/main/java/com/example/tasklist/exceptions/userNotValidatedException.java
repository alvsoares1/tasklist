package com.example.tasklist.exceptions;

public class userNotValidatedException extends RuntimeException{
    public userNotValidatedException() {super("user not validated");}
    public userNotValidatedException(String message) {super(message);}
}
