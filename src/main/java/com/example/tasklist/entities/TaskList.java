package com.example.tasklist.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "tasklists")
@Getter
@Setter
@NoArgsConstructor
public class TaskList {
    @Id
    private String id;
    private String name;
    private String description;

    @DBRef(lazy = false)
    private List<Task> tasks = new ArrayList<>();

    @DBRef
    private User creator;
}
