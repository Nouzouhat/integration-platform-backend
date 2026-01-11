package com.esiea.integrationplatform.adapters.rest.dto;

import com.esiea.integrationplatform.domain.model.Evenement;

/**
 * Mapper pour convertir entre les DTOs et l'entité métier
 */
public class EvenementDtoMapper {

    /**
     * Convertit un RequestDTO en entité métier
     */
    public static Evenement toDomain(EvenementRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return new Evenement(
                null, // L'ID sera généré par la base de données
                dto.getTitre(),
                dto.getDescription(),
                dto.getDateDebut(),
                dto.getDateFin(),
                dto.getLieu(),
                dto.getCapaciteMax(),
                dto.getTypeEvenement(),
                dto.getStatut()
        );
    }

    /**
     * Convertit une entité métier en ResponseDTO
     */
    public static EvenementResponseDTO toResponseDto(Evenement evenement) {
        if (evenement == null) {
            return null;
        }

        return new EvenementResponseDTO(
                evenement.getId(),
                evenement.getTitre(),
                evenement.getDescription(),
                evenement.getDateDebut(),
                evenement.getDateFin(),
                evenement.getLieu(),
                evenement.getCapaciteMax(),
                evenement.getTypeEvenement(),
                evenement.getStatut()
        );
    }
}