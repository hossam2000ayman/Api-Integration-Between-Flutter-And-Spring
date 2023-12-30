package com.example.springtaskmanagementsystem.service;

import com.example.springtaskmanagementsystem.model.TaskItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskItemService {
    TaskItem addTask(TaskItem taskItem);

    void destroyTasks();

    String deleteTask(int id);

    String updateTask(int id);

    TaskItem readTask(int id);

    List<TaskItem> readAllTasks();

    Page<TaskItem> pageTasks(int page, int pageSize);
    Page<TaskItem> pageTasksWithSort(int page, int pageSize, String field);

}
