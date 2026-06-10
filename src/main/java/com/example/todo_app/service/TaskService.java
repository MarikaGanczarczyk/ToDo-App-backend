package com.example.todo_app.service;

import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo;

    public Task addTask(Task task) {
        return repo.save(task);
    }


    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task getTaskById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    public Task updateTaskById(int id, Task updatedTask) {


        Task existingTask = repo.findById(id).orElseThrow(() -> new RuntimeException("Task not found: " + id));
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setPriority(updatedTask.getPriority());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setDueDate(updatedTask.getDueDate());

        return repo.save(existingTask);

}

}

