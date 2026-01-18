package com.esiea.integrationplatform.infrastructure.mapper;

import com.esiea.integrationplatform.domain.model.Notification;
import com.esiea.integrationplatform.infrastructure.persistence.entity.NotificationEntity;

/**
 * Mapper entre Notification (domaine) et NotificationEntity (JPA)
 */
public class NotificationMapper {

    /**
     * Convertit une entité JPA en modèle domaine
     */
    public static Notification toDomain(NotificationEntity entity) {
        if (entity == null) {
            return null;
        }

        Notification notification = new Notification();
        notification.setId(entity.getId());
        notification.setUserId(entity.getUserId());
        notification.setType(entity.getType());
        notification.setTitre(entity.getTitre());
        notification.setMessage(entity.getMessage());
        notification.setStatut(entity.getStatut());
        notification.setDateCreation(entity.getDateCreation());
        notification.setDateLecture(entity.getDateLecture());
        notification.setEvenementId(entity.getEvenementId());
        notification.setAction(entity.getAction());

        return notification;
    }

    /**
     * Convertit un modèle domaine en entité JPA
     */
    public static NotificationEntity toEntity(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationEntity entity = new NotificationEntity();
        entity.setId(notification.getId());
        entity.setUserId(notification.getUserId());
        entity.setType(notification.getType());
        entity.setTitre(notification.getTitre());
        entity.setMessage(notification.getMessage());
        entity.setStatut(notification.getStatut());
        entity.setDateCreation(notification.getDateCreation());
        entity.setDateLecture(notification.getDateLecture());
        entity.setEvenementId(notification.getEvenementId());
        entity.setAction(notification.getAction());

        return entity;
    }
}
