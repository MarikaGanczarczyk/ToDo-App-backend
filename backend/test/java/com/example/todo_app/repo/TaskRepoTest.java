package com.example.todo_app.repo;

import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


// https://www.baeldung.com/junit-datajpatest-repository
@DataJpaTest
public class TaskRepoTest {


    @Autowired
    private TaskRepo repo;

    private Task testTask;

    @BeforeEach
    public void setUp() {
        testTask = new Task();
        testTask.setTitle("testTaskTitle");
        testTask.setDescription("testTitle description");
        testTask.setStatus(Task.Status.COMPLETED);
        testTask.setPriority(Task.Priority.LOW);
        testTask.setDueDate(LocalDate.parse("2026-02-05"));
        repo.save(testTask);
    }
    @AfterEach
    public void tearDown(){
        repo.deleteAll();
    }

    @Test
    @DisplayName("should find tasks by status")
    void findByStatusTest(){
        List<Task> savedTask = repo.findByStatus(testTask.getStatus());

        assertFalse(savedTask.isEmpty());
        assertEquals(testTask.getStatus(), savedTask.get(0).getStatus());

    }


    @Test
    @DisplayName("Should find tasks by title")
    void findByTitleContainingIgnoreCase(){
        List<Task> savedTask = repo.findByTitleContainingIgnoreCase(testTask.getTitle());

        assertFalse(savedTask.isEmpty());
        assertEquals(1, savedTask.size());
        assertEquals(testTask.getTitle(), savedTask.get(0).getTitle());
    }

  @Test
    @DisplayName("Should find tasks by priority ")
    void findByPriority(){

        List<Task> savedTask = repo.findByPriority(testTask.getPriority());

        assertFalse(savedTask.isEmpty());
        assertEquals(1, savedTask.size());
        assertEquals(testTask.getPriority(), savedTask.get(0).getPriority());
  }

  @Test
    @DisplayName("Should find tasks by dueDate")
    void findByDueDate(){
        List<Task> savedTask = repo.findByDueDate(testTask.getDueDate());

        assertFalse(savedTask.isEmpty());
      assertEquals(1, savedTask.size());
      assertEquals(testTask.getDueDate(), savedTask.get(0).getDueDate());
  }
}
