package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.UserNotFoundException;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.ModifierProfilUseCase;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;
import com.esiea.integrationplatform.domain.service.UserValidator;

/**
 * Implémentation du use case : Modifier le profil d'un utilisateur
 */
public class ModifierProfilUseCaseImpl implements ModifierProfilUseCase {

    private final UserRepositoryPort userRepository;

    public ModifierProfilUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(Long userId, String nom, String prenom,
                       String promotion, String numeroEtudiant, String filiere) {
        
        // 1. Validation de l'ID
        if (userId == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        // 2. Récupérer l'utilisateur existant
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // 3. Modifier uniquement les champs non-null
        boolean modified = false;

        if (nom != null && !nom.trim().isEmpty()) {
            UserValidator.validateNom(nom);
            user.setNom(nom.trim());
            modified = true;
        }

        if (prenom != null && !prenom.trim().isEmpty()) {
            UserValidator.validatePrenom(prenom);
            user.setPrenom(prenom.trim());
            modified = true;
        }

        if (promotion != null && !promotion.trim().isEmpty()) {
            user.setPromotion(promotion.trim());
            modified = true;
        }

        if (numeroEtudiant != null && !numeroEtudiant.trim().isEmpty()) {
            user.setNumeroEtudiant(numeroEtudiant.trim());
            modified = true;
        }

        if (filiere != null && !filiere.trim().isEmpty()) {
            user.setFiliere(filiere.trim());
            modified = true;
        }

        // 4. Sauvegarder si des modifications ont été faites
        if (modified) {
            return userRepository.save(user);
        }

        return user;
    }
}
