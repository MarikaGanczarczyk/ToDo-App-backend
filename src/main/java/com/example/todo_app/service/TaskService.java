package com.example.todo_app.service;

import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo ;

    public Task addTask (Task task){
        return repo.save(task);
    }



}


