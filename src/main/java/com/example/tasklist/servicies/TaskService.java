package com.example.tasklist.servicies;

import com.example.tasklist.entities.Task;
import com.example.tasklist.exceptions.taskNotFoundException;
import com.example.tasklist.exceptions.taskTitleExistingException;
import com.example.tasklist.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public ResponseEntity<Task> createTask(Task task) {
        Optional<Task> existingTask = taskRepository.findByTitle(task.getTitle());

        if(existingTask.isPresent()){
            throw new taskTitleExistingException();
        }
        Task newTask = new Task();
        newTask.setTitle(task.getTitle());
        newTask.setDescription(task.getDescription());
        newTask.setPriority(task.getPriority());
        newTask.setStatus(task.getStatus());
        newTask.setDueDate(task.getDueDate());

        LocalDateTime now = LocalDateTime.now();
        newTask.setCreatedAt(now);
        newTask.setUpdatedAt(now);

        Task savedTask = taskRepository.save(newTask);

        return ResponseEntity.ok(savedTask);

    }

    public ResponseEntity<Task> updateTask(String id, Task taskDetails) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if(existingTask.isEmpty()){
            throw new taskNotFoundException();
        }
        Task task = existingTask.get();
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setPriority(taskDetails.getPriority());
        task.setStatus(taskDetails.getStatus());
        task.setDueDate(taskDetails.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);
        return ResponseEntity.ok(updatedTask);
    }

    public ResponseEntity<Void> deleteTask(String id) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if(existingTask.isEmpty()){
            throw new taskNotFoundException();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
