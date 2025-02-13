package com.teste.teste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.teste.entity.Users;
import com.teste.teste.event.UserEvent;
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
    public ResponseEntity<String> createUser(@Valid @RequestBody Users user) {
        user.setUserId(UUID.randomUUID()); // Gerar o ID antes de enviar pro Kafka
        UserEvent event = new UserEvent(user.getUserId(), user.getUsername(), user.getCpf(), user.getEmail(), "CREATED");

        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaProducerService.sendMessage(message);
            return ResponseEntity.ok("User creation event sent to Kafka");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to send user creation event");
        }
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
    public ResponseEntity<String> updateUser(@PathVariable UUID id, @Valid @RequestBody Users userDetails) {
        UserEvent event = new UserEvent(id, userDetails.getUsername(), userDetails.getCpf(), userDetails.getEmail(), "UPDATED");
    
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaProducerService.sendMessage(message);
            return ResponseEntity.ok("User update event sent to Kafka");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to send user update event");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        UserEvent event = new UserEvent(id, null, null, null, "DELETED");
    
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaProducerService.sendMessage(message);
            return ResponseEntity.ok("User delete event sent to Kafka");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to send user delete event");
        }
    }
    
}
