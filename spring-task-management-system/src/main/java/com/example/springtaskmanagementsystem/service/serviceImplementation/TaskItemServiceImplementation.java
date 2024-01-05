package com.example.springtaskmanagementsystem.service.serviceImplementation;

import com.example.springtaskmanagementsystem.exceptions.exception.TaskItemAlreadyExistException;
import com.example.springtaskmanagementsystem.exceptions.exception.TaskItemNotFoundException;
import com.example.springtaskmanagementsystem.model.TaskItem;
import com.example.springtaskmanagementsystem.repository.TaskItemRepository;
import com.example.springtaskmanagementsystem.service.TaskItemService;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.CalendarScopes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "tasks")
public class TaskItemServiceImplementation implements TaskItemService {

    private TaskItemRepository taskItemRepository;

    private CalendarService calendarService;


    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Task Calendar";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    @Autowired
    public TaskItemServiceImplementation(TaskItemRepository taskItemRepository,

                                         CalendarService calendarService) {
        this.taskItemRepository = taskItemRepository;
        this.calendarService = calendarService;
    }

    @Override
    @CacheEvict(allEntries = true)
    public TaskItem addTask(TaskItem taskItem) throws IOException, GeneralSecurityException {
        if (taskItemRepository.existsTaskItemByTitle(taskItem.getTitle()))
            throw new TaskItemAlreadyExistException("Task already exist, cannot add task with same title");
        calendarService.sendAuthorizedAPI(taskItem);
        calendarService.createEvent(taskItem);
        taskItemRepository.save(taskItem);

        return taskItem;
    }

    @Override
    public void destroyTasks() {
        taskItemRepository.deleteAll();
    }

    @Override
    @CacheEvict(key = "#id")
    public String deleteTask(int id) {
        Optional<TaskItem> taskItemOptional = taskItemRepository.findById(id);
        if (taskItemOptional.isEmpty())
            throw new TaskItemNotFoundException("Task Item not found for delete");
        taskItemRepository.deleteById(id);
        return "Task deleted successfully";
    }

    @Override
    @CachePut(key = "#id")
    public String updateTask(int id) {
        boolean exist = taskItemRepository.existsById(id);
        if (exist) {
            TaskItem task = taskItemRepository.getReferenceById(id);
            boolean done = task.isDone();
            task.setDone(!done);
            taskItemRepository.saveAndFlush(task);
            return "Task updated Successfully";
        }
        throw new TaskItemNotFoundException("Task Item not found for update");
    }

    @Override
    @Cacheable(key = "#id")
    public TaskItem readTask(int id) {
        Optional<TaskItem> taskItemOptional = taskItemRepository.findById(id);
        if (taskItemOptional.isEmpty())
            throw new TaskItemNotFoundException("Task Item not found for read");
        return taskItemOptional.get();

    }

    @Override
    @Cacheable
    public List<TaskItem> readAllTasks() throws GeneralSecurityException, IOException {
        calendarService.sendAuthorizedAPI();
        return taskItemRepository.findAll();
    }

    @Override
    @Cacheable
    public Page<TaskItem> pageTasks(int page, int pageSize) {
        return taskItemRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    @Cacheable
    public Page<TaskItem> pageTasksWithSort(int page, int pageSize, String field) {
        return taskItemRepository.findAll(PageRequest.of(page, pageSize).withSort(Sort.by(field).descending()));
    }
}
