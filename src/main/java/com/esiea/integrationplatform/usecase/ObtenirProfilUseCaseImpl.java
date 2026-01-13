package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.UserNotFoundException;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.ObtenirProfilUseCase;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;

/**
 * Implémentation du use case : Obtenir le profil d'un utilisateur
 */
public class ObtenirProfilUseCaseImpl implements ObtenirProfilUseCase {

    private final UserRepositoryPort userRepository;

    public ObtenirProfilUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(Long userId) {
        
        // Validation
        if (userId == null) {
            throw new IllegalArgumentException("L'ID utilisateur ne peut pas être null");
        }

        // Récupération
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
