package com.example.springtaskmanagementsystem.exceptions.exception;

import jakarta.persistence.EntityNotFoundException;

public class TaskItemNotFoundException extends EntityNotFoundException {
    public TaskItemNotFoundException(String message) {
        super(message);
    }
}
