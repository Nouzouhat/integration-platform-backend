package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.UserNotFoundException;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.SupprimerUserUseCase;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;

/**
 * Implémentation du cas d'usage de suppression d'utilisateur
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class SupprimerUserUseCaseImpl implements SupprimerUserUseCase {

    private final UserRepositoryPort userRepository;

    public SupprimerUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Long id) {
        // 1. Vérifier que l'utilisateur existe
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // 2. Supprimer l'utilisateur
        userRepository.deleteById(id);

        // Note: Dans une version plus avancée, on pourrait :
        // - Publier un événement "UserSupprime" via Kafka
        // - Supprimer les inscriptions associées
        // - Envoyer une notification
    }
}
