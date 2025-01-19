package com.example.devops.controller;

import com.example.devops.model.Task;
import com.example.devops.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        // Arrange
        Task task1 = new Task("Task 1", "Description 1", "TODO", "2024-01-20");
        Task task2 = new Task("Task 2", "Description 2", "IN_PROGRESS", "2024-01-21");
        when(taskService.getAllTasks()).thenReturn(Arrays.asList(task1, task2));

        // Act
        List<Task> result = taskController.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    void testCreateTask() {
        // Arrange
        Task task = new Task("New Task", "New Description", "TODO", "2024-01-20");
        when(taskService.createTask(any(Task.class))).thenReturn(task);

        // Act
        Task result = taskController.createTask(task);

        // Assert
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(taskService, times(1)).createTask(any(Task.class));
    }

    @Test
    void testGetTaskById_Found() {
        // Arrange
        Task task = new Task("Test Task", "Test Description", "TODO", "2024-01-20");
        when(taskService.getTaskById(1L)).thenReturn(Optional.of(task));

        // Act
        ResponseEntity<Task> response = taskController.getTaskById(1L);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Test Task", response.getBody().getTitle());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testGetTaskById_NotFound() {
        // Arrange
        when(taskService.getTaskById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Task> response = taskController.getTaskById(1L);

        // Assert
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(taskService, times(1)).getTaskById(1L);
    }

    @Test
    void testUpdateTask_Success() {
        // Arrange
        Task updatedTask = new Task("Updated Task", "Updated Description", "IN_PROGRESS", "2024-01-21");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        // Act
        ResponseEntity<Task> response = taskController.updateTask(1L, updatedTask);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Updated Task", response.getBody().getTitle());
        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void testUpdateTask_NotFound() {
        // Arrange
        Task updatedTask = new Task("Updated Task", "Updated Description", "IN_PROGRESS", "2024-01-21");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenThrow(new RuntimeException("Task not found"));

        // Act
        ResponseEntity<Task> response = taskController.updateTask(1L, updatedTask);

        // Assert
        assertTrue(response.getStatusCode().is4xxClientError());
        assertNull(response.getBody());
        verify(taskService, times(1)).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void testDeleteTask_Success() {
        // Arrange
        doNothing().when(taskService).deleteTask(1L);

        // Act
        ResponseEntity<?> response = taskController.deleteTask(1L);

        // Assert
        assertTrue(response.getStatusCode().is2xxSuccessful());
        verify(taskService, times(1)).deleteTask(1L);
    }

    @Test
    void testDeleteTask_NotFound() {
        // Arrange
        doThrow(new RuntimeException("Task not found")).when(taskService).deleteTask(1L);

        // Act
        ResponseEntity<?> response = taskController.deleteTask(1L);

        // Assert
        assertTrue(response.getStatusCode().is4xxClientError());
        verify(taskService, times(1)).deleteTask(1L);
    }
}
