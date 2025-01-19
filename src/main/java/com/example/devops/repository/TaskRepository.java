package com.example.devops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.devops.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
