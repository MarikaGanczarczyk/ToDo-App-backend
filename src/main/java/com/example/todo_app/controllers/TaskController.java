package com.example.todo_app.controllers;

import com.example.todo_app.entity.Task;
import com.example.todo_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping("/tasks")
   public Task addTask(@RequestBody Task task){
        return service.addTask(task);
    }


}
