package com.example.springtaskmanagementsystem.exceptions.exception;

import java.io.IOException;

public class FailedToAddEventException extends IOException {

    public FailedToAddEventException(String message) {
        super(message);
    }
}
