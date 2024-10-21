package com.example.tasklist.controllers;

import com.example.tasklist.dtos.LoginDTO;
import com.example.tasklist.dtos.ResponseDTO;
import com.example.tasklist.entities.User;
import com.example.tasklist.servicies.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO data) {
        return userService.loginUser(data);
    }
}
