package com.teste.teste.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.teste.entity.Users;
import com.teste.teste.event.UserEvent;
import com.teste.teste.repository.UsersRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEventConsumerService {

    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;

    public UserEventConsumerService(UsersRepository usersRepository, ObjectMapper objectMapper) {
        this.usersRepository = usersRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "topic-users", groupId = "group_id")
    public void consume(String message) {
        try {
            UserEvent event = objectMapper.readValue(message, UserEvent.class);

            switch (event.getEventType()) {
                case "CREATED":
                    Users user = new Users();
                    user.setUserId(event.getUserId());
                    user.setUsername(event.getUsername());
                    user.setCpf(event.getCpf());
                    user.setEmail(event.getEmail());
                    usersRepository.save(user);
                    break;

                case "UPDATED":
                    Optional<Users> optionalUser = usersRepository.findById(event.getUserId());
                    if (optionalUser.isPresent()) {
                        Users existingUser = optionalUser.get();
                        existingUser.setUsername(event.getUsername());
                        existingUser.setCpf(event.getCpf());
                        existingUser.setEmail(event.getEmail());
                        usersRepository.save(existingUser);
                    }
                    break;

                case "DELETED":
                    usersRepository.deleteById(event.getUserId());
                    break;

                default:
                    System.out.println("Unknown event type: " + event.getEventType());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
