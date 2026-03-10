package com.augustogabriel.taskmanager.controller;

import com.augustogabriel.taskmanager.domain.TaskPriority;
import com.augustogabriel.taskmanager.domain.TaskStatus;
import com.augustogabriel.taskmanager.dto.TaskResponseDTO;
import com.augustogabriel.taskmanager.exception.GlobalExceptionHandler;
import com.augustogabriel.taskmanager.exception.ResourceNotFoundException;
import com.augustogabriel.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(GlobalExceptionHandler.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private TaskResponseDTO buildResponse(UUID id) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(id);
        dto.setName("Study Spring Boot");
        dto.setDescription("Finish controller tests");
        dto.setStatus(TaskStatus.TODO);
        dto.setPriority(TaskPriority.HIGH);
        dto.setDueDate(LocalDate.of(2026,3,20));
        dto.setCreatedAt(Instant.now());
        dto.setUpdatedAt(Instant.now());
        return dto;
    }

    @Test
    void createTask_shouldReturn201() throws Exception {

        UUID id = UUID.randomUUID();
        TaskResponseDTO response = buildResponse(id);

        when(taskService.create(any())).thenReturn(response);

        String body = """
                {
                  "name": "Study Spring Boot",
                  "description": "Finish controller tests",
                  "priority": "HIGH",
                  "dueDate": "2026-03-20"
                }
                """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Study Spring Boot"))
                .andExpect(jsonPath("$.priority").value("HIGH"));
    }

    @Test
    void createTask_invalidBody_shouldReturn400() throws Exception {

        String body = """
                {
                  "description": "missing name"
                }
                """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTaskById_shouldReturn200() throws Exception {

        UUID id = UUID.randomUUID();
        TaskResponseDTO response = buildResponse(id);

        when(taskService.getTaskById(id)).thenReturn(response);

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void getTaskById_whenNotFound_shouldReturn404() throws Exception {

        UUID id = UUID.randomUUID();

        when(taskService.getTaskById(id))
                .thenThrow(new ResourceNotFoundException("Task not found with id: " + id));

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTasks_shouldReturn200() throws Exception {

        UUID id = UUID.randomUUID();
        TaskResponseDTO response = buildResponse(id);

        when(taskService.listAll(
                any(),
                any(),
                any(),
                any()))
                .thenReturn(new PageImpl<>(List.of(response), PageRequest.of(0,10),1));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id.toString()));
    }

    @Test
    void updateTask_shouldReturn200() throws Exception {

        UUID id = UUID.randomUUID();
        TaskResponseDTO response = buildResponse(id);
        response.setName("Updated Task");

        when(taskService.updateTask(eq(id), any())).thenReturn(response);

        String body = """
                {
                  "name": "Updated Task",
                  "priority": "MEDIUM",
                  "status": "IN_PROGRESS"
                }
                """;

        mockMvc.perform(put("/tasks/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Task"));
    }

    @Test
    void deleteTask_shouldReturn200() throws Exception {

        UUID id = UUID.randomUUID();

        doNothing().when(taskService).deleteTask(id);

        mockMvc.perform(delete("/tasks/{id}", id))
                .andExpect(status().isOk());
    }
}