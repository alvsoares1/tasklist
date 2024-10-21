package com.example.tasklist.controllers;

import com.example.tasklist.entities.Task;
import com.example.tasklist.entities.TaskList;
import com.example.tasklist.servicies.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasklists")
public class TaskListController {
    @Autowired
    private TaskListService taskListService;


    @PostMapping("/create")
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskList taskList, @RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        return taskListService.createTaskList(taskList, jwt);
    }


    @PostMapping("/{taskListId}/tasks")
    public ResponseEntity<TaskList> createTaskInTaskList(@PathVariable String taskListId, @RequestBody Task task) {
        return taskListService.createTaskInTaskList(taskListId, task);
    }
}
