package com.example.tasklist.servicies;


import com.example.tasklist.entities.Task;
import com.example.tasklist.entities.TaskList;
import com.example.tasklist.entities.User;
import com.example.tasklist.infra.TokenService;
import com.example.tasklist.repositories.TaskListRepository;
import com.example.tasklist.repositories.TaskRepository;
import com.example.tasklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {
    @Autowired
    TaskListRepository taskListRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenService tokenService;


    public ResponseEntity<TaskList> createTaskList(TaskList taskList, String token) {
        String username = tokenService.validateToken(token);
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userOptional.get();

        Optional<TaskList> existingTaskList = taskListRepository.findTaskListByName(taskList.getName());
        if(existingTaskList.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        TaskList newTaskList = new TaskList();
        newTaskList.setName(taskList.getName());
        newTaskList.setDescription(taskList.getDescription());
        newTaskList.setCreator(user);

        taskListRepository.save(newTaskList);

        user.getTaskLists().add(newTaskList);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTaskList);
    }


    public ResponseEntity<TaskList> createTaskInTaskList(String taskListId, Task task) {
        Optional<TaskList> existingTaskList = taskListRepository.findById(taskListId);

        if (!existingTaskList.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        TaskList taskList = existingTaskList.get();

        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);

        taskList.getTasks().add(savedTask);

        taskListRepository.save(taskList);

        return ResponseEntity.ok(taskList);
    }

    public ResponseEntity<List<TaskList>> getAllTaskLists(String token) {
        String username = tokenService.validateToken(token);
        if (username == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userOptional.get();
        List<TaskList> taskLists = user.getTaskLists();
        if(taskLists.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(taskLists);
    }

}
