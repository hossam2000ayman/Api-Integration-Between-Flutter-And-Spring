package com.example.springtaskmanagementsystem.exceptions.exceptionHandler;

import com.example.springtaskmanagementsystem.exceptions.errorResponse.TaskItemErrorResponse;
import com.example.springtaskmanagementsystem.exceptions.exception.TaskItemAlreadyExistException;
import com.example.springtaskmanagementsystem.exceptions.exception.TaskItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskItemHandler {
    @ExceptionHandler
    public ResponseEntity<TaskItemErrorResponse> handleTaskNotFound(TaskItemNotFoundException taskItemNotFoundException){
        TaskItemErrorResponse taskItemErrorResponse = new TaskItemErrorResponse();
        taskItemErrorResponse.setMessage(taskItemNotFoundException.getMessage());
        taskItemErrorResponse.setStatus(HttpStatus.NOT_FOUND);
        taskItemErrorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(taskItemErrorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<TaskItemErrorResponse> handleTaskAlreadyExist(TaskItemAlreadyExistException taskItemAlreadyExistException){
        TaskItemErrorResponse taskItemErrorResponse = new TaskItemErrorResponse();
        taskItemErrorResponse.setMessage(taskItemAlreadyExistException.getMessage());
        taskItemErrorResponse.setStatus(HttpStatus.ALREADY_REPORTED);
        taskItemErrorResponse.setStatusCode(HttpStatus.ALREADY_REPORTED.value());

        return new ResponseEntity<>(taskItemErrorResponse, HttpStatus.ALREADY_REPORTED);
    }


    @ExceptionHandler
    public ResponseEntity<TaskItemErrorResponse> handleAnyBadRequest(Exception exception){
        TaskItemErrorResponse taskItemErrorResponse = new TaskItemErrorResponse();
        taskItemErrorResponse.setMessage(exception.getMessage());
        taskItemErrorResponse.setStatus(HttpStatus.BAD_REQUEST);
        taskItemErrorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(taskItemErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
