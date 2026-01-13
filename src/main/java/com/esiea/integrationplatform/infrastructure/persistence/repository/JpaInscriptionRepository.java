package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.infrastructure.persistence.entity.InscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository Spring Data JPA pour les inscriptions
 */
public interface JpaInscriptionRepository extends JpaRepository<InscriptionEntity, Long> {

    List<InscriptionEntity> findByEtudiantId(Long etudiantId);

    List<InscriptionEntity> findByEvenementId(Long evenementId);

    boolean existsByEtudiantIdAndEvenementId(Long etudiantId, Long evenementId);

    long countByEvenementIdAndStatut(Long evenementId, String statut);
}
