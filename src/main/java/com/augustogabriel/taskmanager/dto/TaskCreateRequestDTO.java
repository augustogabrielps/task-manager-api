package com.augustogabriel.taskmanager.dto;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Data
@Schema(name = "TaskCreateRequest", description = "Payload userd to create a new task")
public class TaskCreateRequestDTO {

    @Schema(
            description = "Task name",
            example = "Shopping",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "name is required")
    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @Schema(
            description = "Detailed description of the task",
            example = "Go to the mall and buy milk, bread, and eggs"
    )
    @Size(max = 1000, message = "description must be at most 1000 characters")
    private String description;

    @Schema(
            description = "Priority level of the task",
            example = "HIGH"
    )
    private TaskPriority priority;

    @Schema(
            description = "Due date of the task",
            example = "2026-03-10"
    )
    @FutureOrPresent(message = "dueDate must be today or in the future")
    private LocalDate dueDate;

}
