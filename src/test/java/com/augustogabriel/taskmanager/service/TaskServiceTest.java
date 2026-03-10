package com.augustogabriel.taskmanager.service;

import com.augustogabriel.taskmanager.domain.Task;
import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import com.augustogabriel.taskmanager.dto.TaskCreateRequestDTO;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.dto.TaskUpdateRequestDTO;
import com.augustogabriel.taskmanager.exception.ResourceNotFoundException;
import com.augustogabriel.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;
    private UUID taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();

        task = new Task();
        task.setId(taskId);
        task.setName("Study Spring Boot");
        task.setDescription("Learn service tests");
        task.setStatus(TaskStatus.TODO);
        task.setPriority(TaskPriority.HIGH);
        task.setDueDate(LocalDate.of(2026, 3, 20));
        task.setCreatedAt(Instant.parse("2026-03-09T20:00:00Z"));
        task.setUpdatedAt(Instant.parse("2026-03-09T20:10:00Z"));
    }

    @Test
    void create_shouldUseMediumPriorityWhenPriorityIsNull() {
        TaskCreateRequestDTO dto = new TaskCreateRequestDTO();
        dto.setName("New task");
        dto.setDescription("Task without priority");
        dto.setDueDate(LocalDate.of(2026, 3, 25));
        dto.setPriority(null);

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(taskId);
            savedTask.setStatus(TaskStatus.TODO);
            savedTask.setCreatedAt(Instant.parse("2026-03-09T20:00:00Z"));
            savedTask.setUpdatedAt(Instant.parse("2026-03-09T20:10:00Z"));
            return savedTask;
        });

        TaskResponseDTO response = taskService.create(dto);

        assertNotNull(response);
        assertEquals("New task", response.getName());
        assertEquals(TaskPriority.MEDIUM, response.getPriority());

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        Task capturedTask = taskCaptor.getValue();
        assertEquals(TaskPriority.MEDIUM, capturedTask.getPriority());
    }

    @Test
    void getTaskById_shouldReturnTaskWhenFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        TaskResponseDTO response = taskService.getTaskById(taskId);

        assertNotNull(response);
        assertEquals(taskId, response.getId());
        assertEquals("Study Spring Boot", response.getName());
        assertEquals(TaskPriority.HIGH, response.getPriority());
    }

    @Test
    void getTaskById_shouldThrowResourceNotFoundExceptionWhenTaskDoesNotExist() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.getTaskById(taskId)
        );

        assertEquals("Task not found with id: " + taskId, exception.getMessage());
    }

    @Test
    void updateTask_shouldUpdateOnlyProvidedFields() {
        TaskUpdateRequestDTO dto = new TaskUpdateRequestDTO();
        dto.setName("Updated task");
        dto.setPriority(TaskPriority.MEDIUM);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponseDTO response = taskService.updateTask(taskId, dto);

        assertNotNull(response);
        assertEquals("Updated task", response.getName());
        assertEquals(TaskPriority.MEDIUM, response.getPriority());

        assertEquals("Learn service tests", response.getDescription());
        assertEquals(TaskStatus.TODO, response.getStatus());
        assertEquals(LocalDate.of(2026, 3, 20), response.getDueDate());
    }

    @Test
    void updateTask_shouldThrowResourceNotFoundExceptionWhenTaskDoesNotExist() {
        TaskUpdateRequestDTO dto = new TaskUpdateRequestDTO();
        dto.setName("Updated task");

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.updateTask(taskId, dto)
        );

        assertEquals("Task not found with id: " + taskId, exception.getMessage());
    }

    @Test
    void deleteTask_shouldDeleteTaskWhenFound() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.existsById(taskId)).thenReturn(false);

        taskService.deleteTask(taskId);

        verify(taskRepository).delete(task);
    }

    @Test
    void deleteTask_shouldThrowResourceNotFoundExceptionWhenTaskDoesNotExist() {
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.deleteTask(taskId)
        );

        assertEquals("Task not found with id: " + taskId, exception.getMessage());
        verify(taskRepository, never()).delete(any(Task.class));
    }
}