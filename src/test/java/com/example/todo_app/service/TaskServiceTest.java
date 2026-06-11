package com.example.todo_app.service;



import com.example.todo_app.entity.Task;
import com.example.todo_app.repository.TaskRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @InjectMocks
    private TaskService task;

    @Mock
    private TaskRepo repo;

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


    @Test
    @DisplayName("Should save task correctly")
    void shouldAddTask(){
        when(repo.save(sampleTask)).thenReturn(sampleTask);

        Task result = task.addTask(sampleTask);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Task");
        verify(repo, times(1)).save(sampleTask);
    }

    @Test
    @DisplayName("should remove task")
    void deleteTaskById_ShouldCallRepoDeleteById() {
        doNothing().when(repo).deleteById(1);

        task.deleteTaskById(1);

        verify(repo, times(1)).deleteById(1);
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnTask() {
        when(repo.findById(1)).thenReturn(Optional.of(sampleTask));

        Task result = task.getTaskById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    void getTaskById_WhenNotFound_ShouldThrowException() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> task.getTaskById(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Task not found: 99");
    }
    @Test
    void updateTaskById_WhenNotFound_ShouldThrowException() {
        when(repo.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> task.updateTaskById(99, sampleTask))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Task not found: 99");
    }
}
