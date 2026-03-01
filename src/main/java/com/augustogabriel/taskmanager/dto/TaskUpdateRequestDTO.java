package com.augustogabriel.taskmanager.dto;

import lombok.Data;

@Data
public class TaskUpdateRequestDTO {
    private String name;
    private String description;
    private String status;
    private String priority;
    private String dueDate;
}
