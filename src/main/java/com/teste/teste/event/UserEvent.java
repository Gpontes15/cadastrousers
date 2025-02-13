package com.teste.teste.event;

import java.util.UUID;

public class UserEvent {

    private UUID userId;
    private String username;
    private String cpf;
    private String email;
    private String eventType; // CREATED, UPDATED, DELETED

    public UserEvent() {}

    public UserEvent(UUID userId, String username, String cpf, String email, String eventType) {
        this.userId = userId;
        this.username = username;
        this.cpf = cpf;
        this.email = email;
        this.eventType = eventType;
    }

    // Getters e Setters

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
