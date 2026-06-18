package com.example.todo_app.controller;

import com.example.todo_app.controllers.TaskController;
import com.example.todo_app.entity.Task;
import com.example.todo_app.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private Task sampleTask;


    @BeforeEach
    void setUp() {
        sampleTask = new Task();
        sampleTask.setId(1L);
        sampleTask.setTitle("Test Task");
        sampleTask.setDescription("Test Description");
        sampleTask.setPriority(Task.Priority.HIGH);
        sampleTask.setStatus(Task.Status.PENDING);
        sampleTask.setDueDate(LocalDate.now().plusDays(3));
    }


    //Get task
    @Test
    @DisplayName("Should return 200 and list of tasks")
    void getAllTask() throws Exception{
        when(taskService.getAllTasks(null,null,null)).thenReturn(List.of(sampleTask));

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Task"))
                .andExpect(jsonPath("$[0].priority").value("HIGH"))
                .andExpect(jsonPath("$[0].status").value("PENDING"));

    }
}
