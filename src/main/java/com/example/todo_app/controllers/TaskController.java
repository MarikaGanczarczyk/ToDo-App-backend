package com.example.todo_app.controllers;


import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepo;
import com.example.todo_app.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskService service;
    @Autowired
    private TaskRepo repo;

    @PostMapping("/tasks")
   public Task addTask(@Valid @RequestBody Task task){
        return service.addTask(task);
    }

   // Get /tasks   &  //  GET /api/v1/tasks?sort=priority  &  //   GET /api/v1/tasks?sort=dueDate  & //   GET /api/v1/tasks?status=PENDING
   @GetMapping("/tasks")
   public List<Task> getAllTasks(
                                 @RequestParam(required = false) Task.Priority priority,
                                 @RequestParam(required = false) LocalDate dueDate,
                                 @RequestParam(required = false) Task.Status status
   ) {

           return service.getAllTasks( priority, dueDate, status);


   }

   //  GET /api/v1/tasks/{id}
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable int id){

        Task task = service.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //  PUT /api/v1/tasks/{id}
    @PutMapping("tasks/{id}")
    public ResponseEntity<Task> updateTaskById(@PathVariable int id, @RequestBody Task updatedTask){
        Task task = service.updateTaskById( id, updatedTask);
        return ResponseEntity.ok(task);
    }


  //  DELETE /api/v1/tasks/{id}
    @DeleteMapping("tasks/{id}")
    public void deleteTask(@PathVariable int id){
         service.deleteTaskById(id);
    }

//    PATCH /api/v1/tasks/{id}/complete
    @PatchMapping("tasks/{id}/complete")
    public Task updateStatusToCompleted(@PathVariable int id){
     return  service.updateStatusToCompleted(id);
}


  //  GET /api/v1/tasks/search?keyword=project
 @GetMapping("tasks/search")
    public List<Task> findByTitleContainingIgnoreCase(@RequestParam String keyword){
        return service.findByTitleContainingIgnoreCase(keyword);
 }




}
