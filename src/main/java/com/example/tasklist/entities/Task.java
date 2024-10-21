package com.example.tasklist.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "tasks")
@Getter
@Setter
@NoArgsConstructor
public class Task{
    @Id
    private String id;
    private String title;
    private String description;
    private String priority;
    private String status;

    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
