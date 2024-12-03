package com.example.tasklist.exceptions;

public class tasklistAlreadyExistingException extends RuntimeException{
    public tasklistAlreadyExistingException(){super("Tasklist not found");}
    public tasklistAlreadyExistingException(String message){super(message);}
}
