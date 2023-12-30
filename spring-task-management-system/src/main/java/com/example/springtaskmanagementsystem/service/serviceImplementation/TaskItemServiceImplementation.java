package com.example.springtaskmanagementsystem.service.serviceImplementation;

import com.example.springtaskmanagementsystem.exceptions.exception.TaskItemAlreadyExistException;
import com.example.springtaskmanagementsystem.exceptions.exception.TaskItemNotFoundException;
import com.example.springtaskmanagementsystem.model.TaskItem;
import com.example.springtaskmanagementsystem.repository.TaskItemRepository;
import com.example.springtaskmanagementsystem.service.TaskItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskItemServiceImplementation implements TaskItemService {

    private TaskItemRepository taskItemRepository;

    @Autowired
    public TaskItemServiceImplementation(TaskItemRepository taskItemRepository) {
        this.taskItemRepository = taskItemRepository;
    }

    @Override
    public TaskItem addTask(TaskItem taskItem) {
        if(taskItemRepository.existsTaskItemByTitle(taskItem.getTitle()))
            throw new TaskItemAlreadyExistException("Task already exist, cannot add task with same title");

        return taskItemRepository.save(taskItem);
    }

    @Override
    public void destroyTasks() {
        taskItemRepository.deleteAll();
    }

    @Override
    public String deleteTask(int id) {
        Optional<TaskItem> taskItemOptional = taskItemRepository.findById(id);
        if (taskItemOptional.isEmpty())
            throw new TaskItemNotFoundException("Task Item not found for delete");
        taskItemRepository.deleteById(id);
        return "Task deleted successfully";
    }

    @Override
    public String updateTask(int id) {
        boolean exist = taskItemRepository.existsById(id);
        if(exist){
            TaskItem task = taskItemRepository.getReferenceById(id);
            boolean done = task.isDone();
            task.setDone(!done);
            taskItemRepository.saveAndFlush(task);
            return "Task updated Successfully";
        }
        throw new TaskItemNotFoundException("Task Item not found for update");
    }

    @Override
    public TaskItem readTask(int id) {
        Optional<TaskItem> taskItemOptional = taskItemRepository.findById(id);
        if (taskItemOptional.isEmpty())
            throw new TaskItemNotFoundException("Task Item not found for read");
        return taskItemOptional.get();

    }

    @Override
    public List<TaskItem> readAllTasks() {
        return taskItemRepository.findAll();
    }

    @Override
    public Page<TaskItem> pageTasks(int page, int pageSize) {
        return taskItemRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<TaskItem> pageTasksWithSort(int page, int pageSize, String field) {
        return taskItemRepository.findAll(PageRequest.of(page, pageSize).withSort(Sort.by(field).descending()));
    }
}
