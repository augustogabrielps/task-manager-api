package com.augustogabriel.taskmanager.dto;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateRequestDTO {

    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @Size(max = 1000, message = "description must be at most 1000 characters")
    private String description;

    private TaskPriority priority;

    @FutureOrPresent(message = "dueDate must be today or in the future")
    private LocalDate dueDate;

}
