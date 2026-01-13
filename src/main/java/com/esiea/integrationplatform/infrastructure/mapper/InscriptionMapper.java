package com.esiea.integrationplatform.infrastructure.mapper;

import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.infrastructure.persistence.entity.InscriptionEntity;

/**
 * Mapper pour convertir entre Inscription (domaine) et InscriptionEntity (infrastructure)
 */
public class InscriptionMapper {

    /**
     * Convertit une entité JPA en objet métier
     */
    public static Inscription toDomain(InscriptionEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Inscription(
                entity.getId(),
                entity.getEtudiantId(),
                entity.getEvenementId(),
                entity.getStatut(),
                entity.getDateInscription(),
                entity.getCommentaire()
        );
    }

    /**
     * Convertit un objet métier en entité JPA
     */
    public static InscriptionEntity toEntity(Inscription inscription) {
        if (inscription == null) {
            return null;
        }

        return new InscriptionEntity(
                inscription.getId(),
                inscription.getEtudiantId(),
                inscription.getEvenementId(),
                inscription.getStatut(),
                inscription.getDateInscription(),
                inscription.getCommentaire()
        );
    }
}
