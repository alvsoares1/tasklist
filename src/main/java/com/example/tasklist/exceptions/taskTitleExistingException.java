package com.example.tasklist.exceptions;

public class taskTitleExistingException extends RuntimeException{
    public taskTitleExistingException(){super("Title already exists");}
    public taskTitleExistingException(String message){super(message);}
}
