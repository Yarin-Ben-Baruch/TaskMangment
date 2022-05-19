package com.example.task.management.system.exceptions;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
