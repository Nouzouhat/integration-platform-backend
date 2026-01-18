package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée quand une notification n'est pas trouvée
 */
public class NotificationNotFoundException extends RuntimeException {

    private final Long notificationId;

    public NotificationNotFoundException(Long notificationId) {
        super("Notification non trouvée avec l'ID: " + notificationId);
        this.notificationId = notificationId;
    }

    public Long getNotificationId() {
        return notificationId;
    }
}
