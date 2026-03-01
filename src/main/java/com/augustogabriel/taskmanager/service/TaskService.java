package com.augustogabriel.taskmanager.service;

import com.augustogabriel.taskmanager.domain.Task;
import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import com.augustogabriel.taskmanager.dto.TaskCreateRequestDTO;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.dto.TaskUpdateRequestDTO;
import com.augustogabriel.taskmanager.exception.ResourceNotFoundException;
import com.augustogabriel.taskmanager.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final String DELETED_TASK = "Task deleted with id: ";

    public TaskService(TaskRepository taskRepository) {
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

    public Page<TaskResponseDTO> listAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public TaskResponseDTO getTaskById(UUID id) {
        return taskRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public TaskResponseDTO updateTask(UUID id, TaskUpdateRequestDTO taskUpdateRequestDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (taskUpdateRequestDTO.getName() != null) task.setName(taskUpdateRequestDTO.getName());
        if (taskUpdateRequestDTO.getDescription() != null) task.setDescription(taskUpdateRequestDTO.getDescription());
        if (taskUpdateRequestDTO.getStatus() != null)
            task.setStatus(TaskStatus.valueOf(taskUpdateRequestDTO.getStatus()));
        if (taskUpdateRequestDTO.getPriority() != null)
            task.setPriority(TaskPriority.valueOf(taskUpdateRequestDTO.getPriority()));
        if (taskUpdateRequestDTO.getDueDate() != null)
            task.setDueDate(LocalDate.parse(taskUpdateRequestDTO.getDueDate()));

        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    public void deleteTask (UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
        taskRepository.delete(task);
        if(!taskRepository.existsById(id)){
            System.out.println(DELETED_TASK + id);
        }
    }

    private TaskResponseDTO toResponse(Task task){
        TaskResponseDTO response = new TaskResponseDTO();
        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setDueDate(task.getDueDate());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        return response;
    }
}
