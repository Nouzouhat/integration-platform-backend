package com.esiea.integrationplatform.adapters.kafka;

import java.time.LocalDateTime;

/**
 * Classe représentant l'événement Kafka publié quand un utilisateur effectue une action
 */
public class UserActionEvent {

    private Long userId;
    private String action;
    private String details;
    private LocalDateTime timestamp;

    public UserActionEvent() {
    }

    public UserActionEvent(Long userId, String action, String details, LocalDateTime timestamp) {
        this.userId = userId;
        this.action = action;
        this.details = details;
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserActionEvent{" +
                "userId=" + userId +
                ", action='" + action + '\'' +
                ", details='" + details + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
