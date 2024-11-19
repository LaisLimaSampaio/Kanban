package com.example.Kanban.service;

import com.example.Kanban.repository.TaskRepository;
import com.example.Kanban.model.task.Prioridade;
import com.example.Kanban.model.task.Status;
import com.example.Kanban.model.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public Task save(Task task) {
        return repository.save(task);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Task findById(Long id) {
        return repository.findById(id).orElse(null);
    }
    public List<List<Task>> findAll() {

        List<Task> TaskFazer = repository.findByStatus(Status.A_FAZER);
        List<Task> TaskProgresso = repository.findByStatus(Status.EM_PROGRESSO);
        List<Task> TaskConcluido = repository.findByStatus(Status.CONCLUIDO);

        List<List<Task>> lista = new ArrayList<>();

        lista.add(TaskFazer);
        lista.add(TaskProgresso);
        lista.add(TaskConcluido);

        lista = ordenarPorPrioridade(lista);
        return lista;
    }
    public List<List<Task>> ordenarPorPrioridade(List<List<Task>> lista) {
        List<List<Task>> listaCompleta = new ArrayList<>();

        for (List<Task> colunaTasks : lista) {
            List<Task> listaPrioAlta = new ArrayList<>();
            List<Task> listaPrioMedia = new ArrayList<>();
            List<Task> listaPrioBaixa = new ArrayList<>();

            for (Task task : colunaTasks) {
                switch (task.getPriority()) {
                    case ALTA:
                        listaPrioAlta.add(task);
                        break;
                    case MEDIA:
                        listaPrioMedia.add(task);
                        break;
                    case BAIXA:
                        listaPrioBaixa.add(task);
                        break;
                }
            }

            List<Task> listaOrdenada = new ArrayList<>();
            listaOrdenada.addAll(listaPrioAlta);
            listaOrdenada.addAll(listaPrioMedia);
            listaOrdenada.addAll(listaPrioBaixa);

            listaCompleta.add(listaOrdenada);
        }

        return listaCompleta;
    }


    public Task update(Task taskAlterada) {
        Task taskAntiga = repository.findById(taskAlterada.getId()).
                orElseThrow(() -> new RuntimeException("Task não encontrado com o id: " + taskAlterada.getId()));

        Task taskAtualizada = new Task(
                taskAntiga.getId(),
                taskAlterada.getTitle() != null ? taskAlterada.getTitle() : taskAntiga.getTitle(),
                taskAlterada.getDescription() != null ? taskAlterada.getDescription() : taskAntiga.getDescription(),
                taskAntiga.getCreatedDate(), // Normalmente, a data de criação não deve ser alterada
                taskAlterada.getDueDate() != null ? taskAlterada.getDueDate() : taskAntiga.getDueDate(),
                taskAlterada.getPriority() != null ? taskAlterada.getPriority() : taskAntiga.getPriority(),
                taskAlterada.getStatus() != null ? taskAlterada.getStatus() : taskAntiga.getStatus()
        );

        return repository.save(taskAtualizada);
    }

    public Task moverTask(Long id) {
        Task task = repository.findById(id).
            orElseThrow(() -> new RuntimeException("Task não encontrado com o id: " + id));
        if (task.getStatus().equals(Status.A_FAZER)){
            task.setStatus(Status.EM_PROGRESSO);
        }
        else if(task.getStatus().equals(Status.EM_PROGRESSO)){
            task.setStatus(Status.CONCLUIDO);
        }
        else if (task.getStatus().equals(Status.CONCLUIDO)){
            new RuntimeException("A Task já está concluida");
        }
        return repository.save(task);

    }
    public List<Task> filtrarDueDate(LocalDate dueDate){
        return repository.findBydueDate(dueDate);
    }
    public List<Task> filtrarPrioridade(Prioridade prioridade){
        return repository.findByPriority(prioridade);
    }
    public List<Task> filtrarAtrasadas(){
        List<Task> taskNaoFeitas = new ArrayList<>();
        List<Task> taskAtrasada = new ArrayList<>();
        LocalDate agora = LocalDate.now();

        taskNaoFeitas.addAll(repository.findByStatus(Status.A_FAZER));
        taskNaoFeitas.addAll(repository.findByStatus(Status.EM_PROGRESSO));

        for(Task task : taskNaoFeitas){
            if (agora.isAfter(task.getDueDate())){
                taskAtrasada.add(task);
            }
        }
        return taskAtrasada;
    }

}
