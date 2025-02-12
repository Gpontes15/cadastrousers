package com.teste.teste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.teste.entity.Users;
import com.teste.teste.repository.UsersRepository;
import com.teste.teste.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users user) {
        Users savedUser = usersRepository.save(user);
        try {
            String message = objectMapper.writeValueAsString(savedUser);
            kafkaProducerService.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable UUID id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable UUID id, @Valid @RequestBody Users userDetails) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setCpf(userDetails.getCpf());
        user.setEmail(userDetails.getEmail());
        Users updatedUser = usersRepository.save(user);
        try {
            String message = objectMapper.writeValueAsString(updatedUser);
            kafkaProducerService.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        Users user = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        usersRepository.delete(user);
        try {
            String message = objectMapper.writeValueAsString(user);
            kafkaProducerService.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.noContent().build();
    }
}
