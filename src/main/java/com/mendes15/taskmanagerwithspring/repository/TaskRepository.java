package com.mendes15.taskmanagerwithspring.repository;

import com.mendes15.taskmanagerwithspring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}