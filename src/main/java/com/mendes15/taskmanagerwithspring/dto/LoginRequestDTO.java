package com.mendes15.taskmanagerwithspring.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}