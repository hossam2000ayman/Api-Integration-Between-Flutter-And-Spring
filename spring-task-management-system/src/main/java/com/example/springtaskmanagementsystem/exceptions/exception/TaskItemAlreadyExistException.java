package com.example.springtaskmanagementsystem.exceptions.exception;

import org.springframework.dao.DuplicateKeyException;

public class TaskItemAlreadyExistException extends DuplicateKeyException {

    public TaskItemAlreadyExistException(String msg) {
        super(msg);
    }
}
