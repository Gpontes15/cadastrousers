package com.teste.teste.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.teste.entity.Users;
import com.teste.teste.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "users_topic", groupId = "group_id")
    public void consume(String message) {
        try {
            Users user = objectMapper.readValue(message, Users.class);
            usersRepository.save(user);
            System.out.println("Consumed and saved user: " + user);
        } catch (Exception e) {
            System.err.println("Failed to consume message: " + message);
            e.printStackTrace();
        }
    }
}