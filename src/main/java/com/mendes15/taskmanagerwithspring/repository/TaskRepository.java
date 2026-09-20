package com.mendes15.taskmanagerwithspring.repository;

import com.mendes15.taskmanagerwithspring.model.Task;
import com.mendes15.taskmanagerwithspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUser(User user);
}