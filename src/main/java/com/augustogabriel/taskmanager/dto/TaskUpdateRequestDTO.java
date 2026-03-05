package com.augustogabriel.taskmanager.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class TaskUpdateRequestDTO {

    @Size(max = 120, message = "name must be at most 120 characters")
    private String name;

    @Size(max = 1000, message = "description must be at most 1000 characters")
    private String description;
    private String status;
    private String priority;

    @FutureOrPresent(message = "dueDate must be today or in the future")
    private LocalDate dueDate;
}
