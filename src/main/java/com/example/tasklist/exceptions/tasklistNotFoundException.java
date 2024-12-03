package com.example.tasklist.exceptions;

public class tasklistNotFoundException extends RuntimeException{
    public tasklistNotFoundException(){super("tasklist not found");}
    public tasklistNotFoundException(String message){super(message);}
}
