package com.esiea.integrationplatform.infrastructure.mapper;

import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity;

/**
 * Mapper pour convertir entre l'entité métier et l'entité JPA
 */
public class EvenementMapper {

    /**
     * Convertit une entité JPA en entité métier
     */
    public static Evenement toDomain(EvenementEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Evenement(
                entity.getId(),
                entity.getTitre(),
                entity.getDescription(),
                entity.getDateDebut(),
                entity.getDateFin(),
                entity.getLieu(),
                entity.getCapaciteMax(),
                entity.getTypeEvenement(),
                entity.getStatut()
        );
    }

    /**
     * Convertit une entité métier en entité JPA
     */
    public static EvenementEntity toEntity(Evenement domain) {
        if (domain == null) {
            return null;
        }

        return new EvenementEntity(
                domain.getId(),
                domain.getTitre(),
                domain.getDescription(),
                domain.getDateDebut(),
                domain.getDateFin(),
                domain.getLieu(),
                domain.getCapaciteMax(),
                domain.getTypeEvenement(),
                domain.getStatut()
        );
    }
}