package com.example.springtaskmanagementsystem.repository;

import com.example.springtaskmanagementsystem.model.TaskItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskItemRepository extends JpaRepository<TaskItem, Integer> {

    boolean existsTaskItemByTitle(String title);
}
