package com.augustogabriel.taskmanager.dto;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateRequest {

    private String name;
    private String description;
    private TaskPriority priority;
    private LocalDate dueDate;

}
