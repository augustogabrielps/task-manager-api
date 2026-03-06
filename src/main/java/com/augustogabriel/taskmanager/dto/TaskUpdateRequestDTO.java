package com.augustogabriel.taskmanager.dto;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "TaskUpdateRequest", description = "Payload used to update an existing task")
public class TaskUpdateRequestDTO {

    @Schema(description = "Updated task name", example = "Refactor TaskService")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @Schema(description = "Updated detailed description of the task", example = "Improve validation errors and add integration tests")
    @Size(max = 1000, message = "description must be at most 1000 characters")
    private String description;

    @Schema(description = "Updated task status", example = "IN_PROGRESS")
    private TaskStatus status;

    @Schema(description = "Updated task priority", example = "MEDIUM")
    private TaskPriority priority;

    @Schema(description = "Updated due date of the task", example = "2026-03-15")
    @FutureOrPresent(message = "dueDate must be today or in the future")
    private LocalDate dueDate;
}