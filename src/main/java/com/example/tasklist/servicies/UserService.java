package com.example.tasklist.servicies;

import com.example.tasklist.dtos.LoginDTO;
import com.example.tasklist.dtos.ResponseDTO;
import com.example.tasklist.entities.User;
import com.example.tasklist.exceptions.existingEmailException;
import com.example.tasklist.exceptions.existingUsernameException;
import com.example.tasklist.exceptions.userNotFoundException;
import com.example.tasklist.exceptions.wrongPasswordException;
import com.example.tasklist.infra.TokenService;
import com.example.tasklist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;


    public ResponseEntity<User> createUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new existingUsernameException();
        } else if (existingEmail.isPresent()) {
            throw new existingEmailException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<ResponseDTO> loginUser(LoginDTO data){
        User user = this.userRepository.findByUsername(data.username()).orElseThrow(userNotFoundException::new);

        if(passwordEncoder.matches(data.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }else{
            throw new wrongPasswordException();
        }
    }
}
