package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.infrastructure.mapper.EvenementMapper;
import com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptateur qui implémente le port EvenementRepositoryPort
 * Fait le lien entre le domaine et l'infrastructure JPA
 */
@Component
public class EvenementRepositoryAdapter implements EvenementRepositoryPort {

    private final JpaEvenementRepository jpaRepository;

    public EvenementRepositoryAdapter(JpaEvenementRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Evenement save(Evenement evenement) {
        EvenementEntity entity = EvenementMapper.toEntity(evenement);
        EvenementEntity savedEntity = jpaRepository.save(entity);
        return EvenementMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Evenement> findById(Long id) {
        return jpaRepository.findById(id)
                .map(EvenementMapper::toDomain);
    }

    @Override
    public List<Evenement> findAll() {
        return jpaRepository.findAll().stream()
                .map(EvenementMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}