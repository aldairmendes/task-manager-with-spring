package com.mendes15.taskmanagerwithspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserResponseDTO {
    private int id;
    private String username;
    private String email;
}
