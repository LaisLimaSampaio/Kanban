package com.example.Kanban.model.task;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

//classe modelo entidade para serem usadas no banco de dados

@Table(name="task")
@Entity(name="Task")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime createdDate;

    private LocalDate dueDate;


    @Enumerated(EnumType.STRING)
    private Prioridade priority;

    @Enumerated(EnumType.STRING)
    private Status status;


    public Task(Task dados) {
        this.title = dados.getTitle();
        this.description = dados.getDescription();
        this.createdDate = LocalDateTime.now();
        this.dueDate = dados.getDueDate();
        this.priority = dados.getPriority();
        this.status = Status.A_FAZER;

    }
}
