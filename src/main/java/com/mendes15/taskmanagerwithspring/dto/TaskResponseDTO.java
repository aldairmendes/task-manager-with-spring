package com.mendes15.taskmanagerwithspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponseDTO {
    private int id;
    private String title;
    private String content;
}