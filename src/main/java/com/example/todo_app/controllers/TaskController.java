package com.example.todo_app.controllers;

import ch.qos.logback.core.status.Status;
import com.example.todo_app.entity.Task;
import com.example.todo_app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping("/tasks")
   public Task addTask(@RequestBody Task task){
        return service.addTask(task);
    }


   @GetMapping("/tasks")
   public List<Task> getAllTasks(){
        return service.getAllTasks();
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
    @DeleteMapping("task/{id}")
    public void deleteTask(@PathVariable int id){
         service.deleteTaskById(id);
    }

//    PATCH /api/v1/tasks/{id}/complete
    @PatchMapping("tasks/{id}/complete")
    public Task updateStatusToCompleted(@PathVariable int id){
     return  service.updateStatusToCompleted(id);
}
    //   GET /api/v1/tasks?status=PENDING
@GetMapping("tasks/")
    public List<Task> getTaskByStatus(@RequestParam Status status){
      return    service.getTaskByStatus(status);
}

  //  GET /api/v1/tasks/search?keyword=project


  //  GET /api/v1/tasks?sort=priority
 //   GET /api/v1/tasks?sort=dueDate


}
