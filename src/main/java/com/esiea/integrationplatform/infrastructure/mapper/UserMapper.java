package com.esiea.integrationplatform.infrastructure.mapper;

import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.infrastructure.persistence.entity.UserEntity;

/**
 * Mapper entre User (domaine) et UserEntity (JPA)
 */
public class UserMapper {

    /**
     * Convertit une entité JPA en modèle domaine
     */
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        User user = new User();
        user.setId(entity.getId());
        user.setEmail(entity.getEmail());
        user.setMotDePasse(entity.getMotDePasse());
        user.setNom(entity.getNom());
        user.setPrenom(entity.getPrenom());
        user.setRole(entity.getRole());
        user.setActif(entity.getActif());
        user.setDateCreation(entity.getDateCreation());
        user.setPromotion(entity.getPromotion());
        user.setNumeroEtudiant(entity.getNumeroEtudiant());
        user.setFiliere(entity.getFiliere());

        return user;
    }

    /**
     * Convertit un modèle domaine en entité JPA
     */
    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setEmail(user.getEmail());
        entity.setMotDePasse(user.getMotDePasse());
        entity.setNom(user.getNom());
        entity.setPrenom(user.getPrenom());
        entity.setRole(user.getRole());
        entity.setActif(user.getActif());
        entity.setDateCreation(user.getDateCreation());
        entity.setPromotion(user.getPromotion());
        entity.setNumeroEtudiant(user.getNumeroEtudiant());
        entity.setFiliere(user.getFiliere());

        return entity;
    }
}
