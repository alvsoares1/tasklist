package com.example.tasklist.repositories;

import com.example.tasklist.entities.TaskList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskListRepository extends MongoRepository<TaskList, String> {
    Optional<TaskList> findTaskListByName(String title);
}
