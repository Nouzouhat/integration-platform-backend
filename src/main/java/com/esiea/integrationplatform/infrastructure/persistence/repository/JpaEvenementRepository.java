package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'entité EvenementEntity
 */
@Repository
public interface JpaEvenementRepository extends JpaRepository<EvenementEntity, Long> {
    // Spring Data JPA génère automatiquement les méthodes CRUD
    // findAll(), findById(), save(), deleteById(), etc.
}