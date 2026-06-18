package com.example.todo_app.service;


import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo repo;

    public Task addTask(Task task) {
        return repo.save(task);
    }


    public List<Task> getAllTasks( Task.Priority priority, LocalDate dueDate, Task.Status status) {

        if (priority != null && status != null) {
            return repo.findByPriorityAndStatus(priority, status);
        }


        if (priority != null) {
            return repo.findByPriority(priority);
        }

        if (dueDate != null) {
            return repo.findByDueDate(dueDate);
        }
       if (status != null){
           return repo.findByStatus(status);
       }
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

    public void deleteTaskById(int id) {
      repo.deleteById(id);
    }


    public Task updateStatusToCompleted(int id) {

        Task task = repo.findById(id).orElseThrow( () -> new RuntimeException("Task not found: " + id));
        task.setStatus(Task.Status.COMPLETED);
        return repo.save(task);
    }

    public  List<Task> getTaskByStatus(@RequestParam Task.Status status) {
        return  repo.findByStatus(status);
    }

    public List<Task> findByTitleContainingIgnoreCase(@RequestParam String keyword){
        return repo.findByTitleContainingIgnoreCase(keyword);
    }


    public long countAllTasks(){
        return repo.count();
    }


    public long countCompletedTasks() {
        return repo.countByStatus(Task.Status.COMPLETED);
    }

    public long countOverdueTasks() {
        return repo.countByStatusAndDueDateBefore(
                Task.Status.PENDING, LocalDate.now()
        );
    }


}

