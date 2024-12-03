package com.example.tasklist.exceptions;

public class taskNotFoundException extends RuntimeException{
    public taskNotFoundException(){super("task not found");}
    public taskNotFoundException(String message){super(message);}
}
