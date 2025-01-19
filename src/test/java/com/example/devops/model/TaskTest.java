package com.example.devops.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Test Task", "Test Description", "TODO", "2024-01-20");
        
        assertEquals("Test Task", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals("TODO", task.getStatus());
        assertEquals("2024-01-20", task.getDueDate());
    }

    @Test
    public void testTaskSettersAndGetters() {
        Task task = new Task();
        
        task.setTitle("Updated Task");
        task.setDescription("Updated Description");
        task.setStatus("IN_PROGRESS");
        task.setDueDate("2024-02-01");
        
        assertEquals("Updated Task", task.getTitle());
        assertEquals("Updated Description", task.getDescription());
        assertEquals("IN_PROGRESS", task.getStatus());
        assertEquals("2024-02-01", task.getDueDate());
    }
}
