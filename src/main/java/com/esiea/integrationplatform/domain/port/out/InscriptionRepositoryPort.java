package com.esiea.integrationplatform.domain.port.out;

import com.esiea.integrationplatform.domain.model.Inscription;

import java.util.List;
import java.util.Optional;

/**
 * Port de sortie pour la persistance des inscriptions
 * Interface du domaine - implémentée par l'infrastructure
 */
public interface InscriptionRepositoryPort {

    Inscription save(Inscription inscription);

    Optional<Inscription> findById(Long id);

    List<Inscription> findByEtudiantId(Long etudiantId);

    List<Inscription> findByEvenementId(Long evenementId);

    boolean existsByEtudiantIdAndEvenementId(Long etudiantId, Long evenementId);

    long countByEvenementIdAndStatut(Long evenementId, String statut);

    void deleteById(Long id);
}
