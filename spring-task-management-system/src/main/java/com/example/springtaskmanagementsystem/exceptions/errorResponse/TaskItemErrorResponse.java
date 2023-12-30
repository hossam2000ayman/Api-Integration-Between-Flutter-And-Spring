package com.example.springtaskmanagementsystem.exceptions.errorResponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class TaskItemErrorResponse {

    private HttpStatus status;

    private String message;

    private int statusCode;
}
