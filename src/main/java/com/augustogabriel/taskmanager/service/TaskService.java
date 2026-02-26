package com.augustogabriel.taskmanager.service;

import com.augustogabriel.taskmanager.domain.Task;
import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.dto.TaskCreateRequest;
import com.augustogabriel.taskmanager.dto.TaskResponse;
import com.augustogabriel.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService (TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse create(TaskCreateRequest request) {
        Task task = new Task();
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        TaskPriority priority = request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM;
        task.setPriority(priority);

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    public List<TaskResponse> listAll(){
        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TaskResponse toResponse(Task task){
        TaskResponse res = new TaskResponse();
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
