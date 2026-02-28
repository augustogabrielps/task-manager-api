package com.augustogabriel.taskmanager.service;

import com.augustogabriel.taskmanager.domain.Task;
import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.dto.TaskCreateRequestDTO;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.exception.ResourceNotFoundException;
import com.augustogabriel.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private static final UUID UUID = null;
    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponseDTO create(TaskCreateRequestDTO taskCreateRequestDTO) {
        Task task = new Task();
        task.setName(taskCreateRequestDTO.getName());
        task.setDescription(taskCreateRequestDTO.getDescription());
        task.setDueDate(taskCreateRequestDTO.getDueDate());

        TaskPriority priority = taskCreateRequestDTO.getPriority() != null ? taskCreateRequestDTO.getPriority() : TaskPriority.MEDIUM;
        task.setPriority(priority);

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public List<TaskResponseDTO> listAll(){
        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponseDTO getTaskById(UUID id) {
        return taskRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    private TaskResponseDTO toResponse(Task task){
        TaskResponseDTO res = new TaskResponseDTO();
        res.setId(task.getId());
        res.setName(task.getName());
        res.setDescription(task.getDescription());
        res.setStatus(task.getStatus());
        res.setPriority(task.getPriority());
        res.setDueDate(task.getDueDate());
        res.setCreatedAt(task.getCreatedAt());
        res.setUpdatedAt(task.getUpdatedAt());
        return res;
    }

}
