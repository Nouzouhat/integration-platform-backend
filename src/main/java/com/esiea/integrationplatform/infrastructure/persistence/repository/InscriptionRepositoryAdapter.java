package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import com.esiea.integrationplatform.infrastructure.mapper.InscriptionMapper;
import com.esiea.integrationplatform.infrastructure.persistence.entity.InscriptionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptateur qui implémente le port InscriptionRepositoryPort
 * Fait le lien entre le domaine et l'infrastructure JPA
 */
@Component
public class InscriptionRepositoryAdapter implements InscriptionRepositoryPort {

    private final JpaInscriptionRepository jpaRepository;

    public InscriptionRepositoryAdapter(JpaInscriptionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Inscription save(Inscription inscription) {
        InscriptionEntity entity = InscriptionMapper.toEntity(inscription);
        InscriptionEntity savedEntity = jpaRepository.save(entity);
        return InscriptionMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Inscription> findById(Long id) {
        return jpaRepository.findById(id)
                .map(InscriptionMapper::toDomain);
    }

    @Override
    public List<Inscription> findByEtudiantId(Long etudiantId) {
        return jpaRepository.findByEtudiantId(etudiantId).stream()
                .map(InscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Inscription> findByEvenementId(Long evenementId) {
        return jpaRepository.findByEvenementId(evenementId).stream()
                .map(InscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEtudiantIdAndEvenementId(Long etudiantId, Long evenementId) {
        return jpaRepository.existsByEtudiantIdAndEvenementId(etudiantId, evenementId);
    }

    @Override
    public long countByEvenementIdAndStatut(Long evenementId, String statut) {
        return jpaRepository.countByEvenementIdAndStatut(evenementId, statut);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
