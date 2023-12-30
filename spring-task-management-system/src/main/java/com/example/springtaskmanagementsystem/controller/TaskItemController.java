package com.example.springtaskmanagementsystem.controller;

import com.example.springtaskmanagementsystem.model.TaskItem;
import com.example.springtaskmanagementsystem.service.TaskItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskItemController {

    private TaskItemService taskItemService;

    @Autowired
    public TaskItemController(TaskItemService taskItemService) {
        this.taskItemService = taskItemService;
    }

    @PostMapping("add")
    public TaskItem addTask(@RequestBody TaskItem taskItem) {
        return taskItemService.addTask(taskItem);
    }


    @DeleteMapping("destroy")
    public void destroyTasks() {
        taskItemService.destroyTasks();
    }

    @DeleteMapping("delete/{id}")
    public String deleteTask(@PathVariable int id) {
        return taskItemService.deleteTask(id);
    }

    @PutMapping("update/{id}")
    public String updateTask(@PathVariable int id) {
        return taskItemService.updateTask(id);
    }

    @GetMapping("read/{id}")
    public TaskItem readTask(@PathVariable int id) {
        return taskItemService.readTask(id);
    }

    @GetMapping("read/all")
    public List<TaskItem> readAllTasks() {
        return taskItemService.readAllTasks();
    }

    @GetMapping("page")
    Page<TaskItem> pageTasks(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "100") int pageSize) {
        return taskItemService.pageTasks(page, pageSize);
    }

    @GetMapping("page/sort")
    Page<TaskItem> pageTasksWithSort(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "100") int pageSize, @RequestParam(required = false, defaultValue = "id") String field) {
        return taskItemService.pageTasksWithSort(page, pageSize, field);
    }


}
