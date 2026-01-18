package com.esiea.integrationplatform.adapters.rest.dto;

import com.esiea.integrationplatform.domain.model.Notification;

/**
 * Mapper entre Notification (domaine) et NotificationResponseDTO
 */
public class NotificationDtoMapper {

    /**
     * Convertit une notification domaine en DTO de réponse
     */
    public static NotificationResponseDTO toResponseDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationResponseDTO dto = new NotificationResponseDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId());
        dto.setType(notification.getType());
        dto.setTitre(notification.getTitre());
        dto.setMessage(notification.getMessage());
        dto.setStatut(notification.getStatut());
        dto.setDateCreation(notification.getDateCreation());
        dto.setDateLecture(notification.getDateLecture());
        dto.setEvenementId(notification.getEvenementId());
        dto.setAction(notification.getAction());

        return dto;
    }
}
