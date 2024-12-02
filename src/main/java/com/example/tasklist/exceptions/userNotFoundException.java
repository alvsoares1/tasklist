package com.example.tasklist.exceptions;

public class userNotFoundException extends RuntimeException{
    public userNotFoundException(){super("user not found");}
    public userNotFoundException(String message){super(message);}
}
