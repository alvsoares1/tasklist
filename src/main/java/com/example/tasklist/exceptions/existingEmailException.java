package com.example.tasklist.exceptions;

public class existingEmailException extends RuntimeException{
    public existingEmailException(){super("Email already exists");}
    public existingEmailException(String message){super(message);}
}
