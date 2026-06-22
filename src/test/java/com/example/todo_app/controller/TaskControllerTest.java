package com.example.todo_app.controller;

import com.example.todo_app.controllers.TaskController;
import com.example.todo_app.entity.Task;
import com.example.todo_app.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    //POST
    @Test
    @DisplayName("Positive: Should create task and return 200")
    void createTask_ShouldReturn200() throws Exception {

        when(taskService.addTask(any(Task.class))).thenReturn(sampleTask);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    @DisplayName("Negative: Should return 400 when task is invalid")
    void createTask_ShouldReturn400_WhenInvalid() throws Exception {

        Task invalidTask = new Task(); //no title

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTask)))
                .andExpect(status().isBadRequest());
    }

    //Get/tasks/{id}
    @Test
    @DisplayName("Positive: Should return task by id")
    void getTaskById_ShouldReturnTask() throws Exception {
        when(taskService.getTaskById(1)).thenReturn(sampleTask);

        mockMvc.perform(get("/api/v1/tasks/1")).andExpect(status().isOk()).andExpect(jsonPath("$.title").value("Test Task"));

    }

    //Put/Task/{id}
    @Test
    @DisplayName("Positive : Should update task")
    void updateTask_ShouldReturnUpdatedTask() throws Exception {

        when(taskService.updateTaskById(eq(1), any(Task.class)))
                .thenReturn(sampleTask);

        mockMvc.perform(put("/api/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTask)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }



    @Test
    @DisplayName("Positive : Should delete task")
    void deleteTask_ShouldReturn200() throws Exception {

        doNothing().when(taskService).deleteTaskById(1);

        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isOk());
    }



    //Patch/task/{id}/complete
    @Test
    @DisplayName("Should mark task as completed")
    void completeTask_ShouldReturnUpdatedTask() throws Exception {

        sampleTask.setStatus(Task.Status.COMPLETED);

        when(taskService.updateStatusToCompleted(1)).thenReturn(sampleTask);

        mockMvc.perform(patch("/api/v1/tasks/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
//SEARCH /tasks/search
@Test
@DisplayName("Should search tasks by keyword")
void searchTask_ShouldReturnMatchingTasks() throws Exception {

    when(taskService.findByTitleContainingIgnoreCase("Test"))
            .thenReturn(List.of(sampleTask));

    mockMvc.perform(get("/api/v1/tasks/search")
                    .param("keyword", "Test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
}
}
