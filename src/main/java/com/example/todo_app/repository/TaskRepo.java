package com.example.todo_app.repository;


import ch.qos.logback.core.status.Status;
import com.example.todo_app.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Integer> {


    List<Task> findByStatus(Task.Status status);

    List<Task> findByTitleContainingIgnoreCase(String keyword);

    List<Task> findByPriority(Task.Priority priority);

    List<Task> findByDueDate(LocalDate dueDate);

    List<Task> findByPriorityAndStatus(Task.Priority priority, Task.Status status);


}
