package com.augustogabriel.taskmanager.controller;

import com.augustogabriel.taskmanager.dto.TaskCreateRequestDTO;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.dto.TaskUpdateRequestDTO;
import com.augustogabriel.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import jdk.jfr.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Description("Create a new task")
    public TaskResponseDTO createTask(@Valid @RequestBody TaskCreateRequestDTO taskCreateRequestDTO) {
        return taskService.create(taskCreateRequestDTO);
    }

    @GetMapping
    @Description("Get a list of all tasks")

    public Page<TaskResponseDTO> getAllTasks(@PageableDefault (size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return taskService.listAll(pageable);
    }

    @GetMapping("/{id}")
    @Description("Get a specific task by its ID")
    public TaskResponseDTO getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }


    @PutMapping("/update/{id}")
    @Description("Update an existing task")
    public TaskResponseDTO getUpdatedTask(@PathVariable UUID id, @Valid @RequestBody TaskUpdateRequestDTO taskUpdateRequest) {
        return taskService.updateTask(id, taskUpdateRequest);
    }

    @DeleteMapping("/delete/{id}")
    @Description("Delete a task by its ID")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }
}

