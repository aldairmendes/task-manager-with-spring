package com.mendes15.taskmanagerwithspring.controller;

import com.mendes15.taskmanagerwithspring.dto.TaskRequestDTO;
import com.mendes15.taskmanagerwithspring.dto.TaskResponseDTO;
import com.mendes15.taskmanagerwithspring.model.Task;
import com.mendes15.taskmanagerwithspring.model.User;
import com.mendes15.taskmanagerwithspring.repository.TaskRepository;
import com.mendes15.taskmanagerwithspring.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Tasks", description = "Gestão de tarefas do utilizador autenticado")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Listar tarefas", description = "Retorna todas as tarefas pertencentes ao utilizador autenticado.")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        List<TaskResponseDTO> tasks = taskRepository.findByUser(user).stream()
                .map(task -> new TaskResponseDTO(task.getId(), task.getTitle(), task.getContent()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Criar tarefa", description = "Cria uma nova tarefa associada ao utilizador autenticado.")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso")
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setContent(taskRequest.getContent());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        TaskResponseDTO response = new TaskResponseDTO(savedTask.getId(), savedTask.getTitle(), savedTask.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualizar tarefa", description = "Atualiza uma tarefa existente, desde que pertença ao utilizador autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada ou não pertence ao utilizador")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @Parameter(description = "ID da tarefa a atualizar") @PathVariable int id,
            @Valid @RequestBody TaskRequestDTO taskRequest,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId() == user.getId())
                .map(existingTask -> {
                    existingTask.setTitle(taskRequest.getTitle());
                    existingTask.setContent(taskRequest.getContent());
                    Task updated = taskRepository.save(existingTask);

                    TaskResponseDTO response = new TaskResponseDTO(updated.getId(), updated.getTitle(), updated.getContent());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Eliminar tarefa", description = "Elimina uma tarefa pelo ID, desde que pertença ao utilizador autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Tarefa eliminada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada ou não pertence ao utilizador")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID da tarefa a eliminar") @PathVariable int id,
            Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        return taskRepository.findById(id)
                .filter(task -> task.getUser().getId() == user.getId())
                .map(existingTask -> {
                    taskRepository.delete(existingTask);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("Utilizador autenticado não encontrado na base de dados."));

    }
}