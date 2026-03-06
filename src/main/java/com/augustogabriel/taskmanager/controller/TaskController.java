package com.augustogabriel.taskmanager.controller;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import com.augustogabriel.taskmanager.dto.TaskCreateRequestDTO;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.dto.TaskUpdateRequestDTO;
import com.augustogabriel.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new task", description = "Creates a new task in the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode =  "201", description = "Task created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public TaskResponseDTO createTask(@Valid @RequestBody TaskCreateRequestDTO taskCreateRequestDTO) {
        return taskService.create(taskCreateRequestDTO);
    }

    @GetMapping
    @Operation(summary = "List tasks", description = "Retrieves a paginated list of tasks with optional filters for status, priority and search query.")
    public Page<TaskResponseDTO> getAllTasks(
            @Parameter(description = "Filter tasks by status", example = "TODO")
            @RequestParam(required = false) TaskStatus status,
            @Parameter(description = "Filter by task priority", example = "HIGH")
            @RequestParam(required = false) TaskPriority priority,
            @Parameter(description = "Search text applied to name and description", example = "spring")
            @RequestParam(required = false, name = "q") String query,
            @Parameter(description = "Pagination and sorting. Example: page=0&size=10&sort=createdAt,desc")
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return taskService.listAll(status, priority, query, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID", description = "Retrieves a task by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task found"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public TaskResponseDTO getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a task by ID", description = "Updates the details of an existing task identified by its unique identifier.")
    public TaskResponseDTO getUpdatedTask(@PathVariable UUID id, @Valid @RequestBody TaskUpdateRequestDTO taskUpdateRequest) {
        return taskService.updateTask(id, taskUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by ID", description = "Deletes an existing task identified by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }
}

