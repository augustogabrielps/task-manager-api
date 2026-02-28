package com.augustogabriel.taskmanager.controller;

import com.augustogabriel.taskmanager.dto.TaskCreateRequestDTO;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.service.TaskService;
import jdk.jfr.Description;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public TaskResponseDTO createTask(@RequestBody TaskCreateRequestDTO taskCreateRequestDTO) {
        return taskService.create(taskCreateRequestDTO);
    }

    @GetMapping
    @Description("Get a list of all tasks")
    public List<TaskResponseDTO> getAllTasks() {
        return taskService.listAll();
    }

    @GetMapping("/{id}")
    @Description("Get a specific task by its ID")
    public TaskResponseDTO getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }
}

