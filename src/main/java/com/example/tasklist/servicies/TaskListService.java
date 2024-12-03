package com.example.tasklist.servicies;


import com.example.tasklist.entities.Task;
import com.example.tasklist.entities.TaskList;
import com.example.tasklist.entities.User;
import com.example.tasklist.exceptions.*;
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
        String tokenValidated = tokenService.validateToken(token);
        if (tokenValidated == null) {
            throw new userNotValidatedException();}

        Optional<User> username = userRepository.findByUsername(tokenValidated);
        if (username.isEmpty()) {
            throw new userNotFoundException();}

        User user = username.get();
        Optional<TaskList> existingTaskList = taskListRepository.findTaskListByName(taskList.getName());
        if(existingTaskList.isPresent()){
            throw new tasklistAlreadyExistingException();
        }
        TaskList newTaskList = new TaskList();
        newTaskList.setName(taskList.getName());
        newTaskList.setDescription(taskList.getDescription());
        newTaskList.setCreator(user);

        taskListRepository.save(newTaskList);

        user.getTaskLists().add(newTaskList);
        userRepository.save(user);
        return ResponseEntity.ok(newTaskList);
    }


    public ResponseEntity<TaskList> createTaskInTaskList(String taskListId, Task task) {
        Optional<TaskList> existingTaskList = taskListRepository.findById(taskListId);

        if (existingTaskList.isEmpty()) {
            throw new tasklistNotFoundException();
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
        String tokenValidated = tokenService.validateToken(token);
        if (tokenValidated == null){
            throw new userNotValidatedException();}


        Optional<User> userOptional = userRepository.findByUsername(tokenValidated);
        if (userOptional.isEmpty()) {
            throw new userNotFoundException();
        }

        User user = userOptional.get();
        List<TaskList> taskLists = user.getTaskLists();
        if(taskLists.isEmpty()){
            throw new tasklistNotFoundException();
        }
        return ResponseEntity.ok(taskLists);
    }

    public ResponseEntity<List<Task>> getTasksByTaskListId(String taskListId) {
        Optional<TaskList> existingTaskList = taskListRepository.findById(taskListId);

        if (existingTaskList.isEmpty()) {
            throw new tasklistNotFoundException();
        }

        TaskList taskList = existingTaskList.get();
        List<Task> tasks = taskList.getTasks();

        return ResponseEntity.ok(tasks);
    }

}
