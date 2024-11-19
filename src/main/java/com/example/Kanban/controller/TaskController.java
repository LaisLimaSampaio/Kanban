package com.example.Kanban.controller;

import com.example.Kanban.service.TaskService;
import com.example.Kanban.model.task.Prioridade;
import com.example.Kanban.model.task.Task;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping ("task")
public class TaskController {
    @Autowired
    private TaskService service;

    @PostMapping
    @Transactional
    public ResponseEntity<Task> criarTask(@RequestBody Task dados){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(new Task(dados)));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarTask(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findById(id));
    }
    @GetMapping(value = "/dueDate/{dueDate}")
    public ResponseEntity<List<Task>> getTaskByDueDate(@PathVariable LocalDate dueDate){
        return ResponseEntity.ok().body(service.filtrarDueDate(dueDate));
    }

    @GetMapping
    public ResponseEntity<List<List<Task>>> getTasks(){
        return ResponseEntity.ok().body(service.findAll());
    }
    @GetMapping(value = "/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Prioridade priority){
        return ResponseEntity.ok().body(service.filtrarPrioridade(priority));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Task> atualizarTask(@PathVariable Long id,@RequestBody Task dados){
        dados.setId(id);
        return ResponseEntity.ok().body(service.update(dados));
    }

    @PutMapping(value = "/{id}/move")
    public ResponseEntity<Task> moveTask(@PathVariable Long id){
        return ResponseEntity.ok().body(service.moverTask(id));
    }

    @GetMapping(value = "/atrasadas")
    public ResponseEntity<List<Task>> getAtrasadas(){
        return ResponseEntity.ok().body(service.filtrarAtrasadas());
    }


}
