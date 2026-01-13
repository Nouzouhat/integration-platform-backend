package com.esiea.integrationplatform.adapters.rest.dto;

import com.esiea.integrationplatform.domain.model.User;

/**
 * Mapper pour convertir User (domaine) en DTOs
 */
public class UserDtoMapper {

    /**
     * Convertit un User en UserResponseDTO (sans mot de passe)
     */
    public static UserResponseDTO toResponseDto(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setRole(user.getRole());
        dto.setActif(user.getActif());
        dto.setDateCreation(user.getDateCreation());
        dto.setPromotion(user.getPromotion());
        dto.setNumeroEtudiant(user.getNumeroEtudiant());
        dto.setFiliere(user.getFiliere());

        return dto;
    }
}
