package com.example.Kanban.repository;

import com.example.Kanban.model.task.Prioridade;
import com.example.Kanban.model.task.Status;
import com.example.Kanban.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{
    List<Task> findByStatus(Status status);
    List<Task> findBydueDate(LocalDate dueDate);
    List<Task> findByPriority(Prioridade priority);

}
