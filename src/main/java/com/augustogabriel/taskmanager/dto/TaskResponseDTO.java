package com.augustogabriel.taskmanager.dto;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(name = "TaskResponse", description = "Representation of a task returned by the API")
public class TaskResponseDTO {

    @Schema(description = "Unique identifier of the task", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Task name", example = "Remember to buy groceries")
    private String name;

    @Schema(description = "Detailed description of the task", example = "Finish the shopping list and buy milk, bread, and eggs")
    private String description;

    @Schema(description = "Current status of the task", example = "TODO")
    private TaskStatus status;

    @Schema(description = "Priority level of the task", example = "HIGH")
    private TaskPriority priority;

    @Schema(description = "Due date of the task", example = "2026-03-10")
    private LocalDate dueDate;

    @Schema(description = "Timestamp when the task was created", example = "2026-03-05T00:15:30Z")
    private Instant createdAt;

    @Schema(description = "Timestamp when the task was last updated", example = "2026-03-05T10:45:12Z")
    private Instant updatedAt;
}