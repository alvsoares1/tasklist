package com.example.tasklist.repositories;

import com.example.tasklist.entities.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {
    Optional<Task> findByTitle(String title);
}
