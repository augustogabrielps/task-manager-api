package com.augustogabriel.taskmanager.dto;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class TaskResponse {

    private UUID id;
    private String name;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private Instant createdAt;
    private Instant updatedAt;

}
