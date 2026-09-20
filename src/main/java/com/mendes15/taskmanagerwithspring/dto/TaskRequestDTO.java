package com.mendes15.taskmanagerwithspring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDTO {
    @NotBlank(message = "O título não pode estar em branco")
    @Size(max = 255, message = "O título deve ter no máximo 255 caracteres")
    private String title;
    private String content;
}