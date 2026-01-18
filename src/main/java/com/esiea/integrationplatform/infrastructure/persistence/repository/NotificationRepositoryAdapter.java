package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.domain.model.Notification;
import com.esiea.integrationplatform.domain.port.out.NotificationRepositoryPort;
import com.esiea.integrationplatform.infrastructure.mapper.NotificationMapper;
import com.esiea.integrationplatform.infrastructure.persistence.entity.NotificationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter qui implémente le port NotificationRepositoryPort
 * Fait le pont entre le domaine et l'infrastructure JPA
 */
@Component
public class NotificationRepositoryAdapter implements NotificationRepositoryPort {

    private final JpaNotificationRepository jpaNotificationRepository;

    public NotificationRepositoryAdapter(JpaNotificationRepository jpaNotificationRepository) {
        this.jpaNotificationRepository = jpaNotificationRepository;
    }

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = NotificationMapper.toEntity(notification);
        NotificationEntity savedEntity = jpaNotificationRepository.save(entity);
        return NotificationMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return jpaNotificationRepository.findById(id)
                .map(NotificationMapper::toDomain);
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        return jpaNotificationRepository.findByUserId(userId).stream()
                .map(NotificationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> findByUserIdAndStatut(Long userId, String statut) {
        return jpaNotificationRepository.findByUserIdAndStatut(userId, statut).stream()
                .map(NotificationMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaNotificationRepository.deleteById(id);
    }
}
