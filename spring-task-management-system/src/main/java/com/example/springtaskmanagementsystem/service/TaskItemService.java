package com.example.springtaskmanagementsystem.service;

import com.example.springtaskmanagementsystem.exceptions.exception.FailedToAddEventException;
import com.example.springtaskmanagementsystem.model.TaskItem;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface TaskItemService {
    TaskItem addTask(TaskItem taskItem) throws IOException, GeneralSecurityException;

    void destroyTasks();

    String deleteTask(int id);

    String updateTask(int id);

    TaskItem readTask(int id);

    List<TaskItem> readAllTasks() throws GeneralSecurityException, IOException;

    Page<TaskItem> pageTasks(int page, int pageSize);
    Page<TaskItem> pageTasksWithSort(int page, int pageSize, String field);

}
