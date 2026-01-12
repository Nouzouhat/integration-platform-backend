package com.esiea.integrationplatform.adapters.rest.dto;

import com.esiea.integrationplatform.domain.model.Inscription;

/**
 * Mapper pour convertir entre Inscription (domaine) et DTOs
 */
public class InscriptionDtoMapper {

    /**
     * Convertit un DTO de requête en objet métier
     */
    public static Inscription toDomain(InscriptionRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Inscription inscription = new Inscription();
        inscription.setEtudiantId(dto.getEtudiantId());
        inscription.setEvenementId(dto.getEvenementId());
        inscription.setCommentaire(dto.getCommentaire());

        return inscription;
    }

    /**
     * Convertit un objet métier en DTO de réponse
     */
    public static InscriptionResponseDTO toResponseDto(Inscription inscription) {
        if (inscription == null) {
            return null;
        }

        return new InscriptionResponseDTO(
                inscription.getId(),
                inscription.getEtudiantId(),
                inscription.getEvenementId(),
                inscription.getStatut(),
                inscription.getDateInscription(),
                inscription.getCommentaire()
        );
    }
}
