package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.InvalidCredentialsException;
import com.esiea.integrationplatform.domain.exception.UserNotFoundException;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.SeConnecterUseCase;
import com.esiea.integrationplatform.domain.port.out.PasswordEncoderPort;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;

/**
 * Implémentation du use case : Se connecter
 */
public class SeConnecterUseCaseImpl implements SeConnecterUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public SeConnecterUseCaseImpl(UserRepositoryPort userRepository,
                                  PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(String email, String motDePasse) {
        
        // 1. Vérifier que les champs ne sont pas vides
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidCredentialsException("L'email est obligatoire");
        }
        if (motDePasse == null || motDePasse.isEmpty()) {
            throw new InvalidCredentialsException("Le mot de passe est obligatoire");
        }

        // 2. Rechercher l'utilisateur par email
        User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new UserNotFoundException(email));

        // 3. Vérifier que le compte est actif
        if (!user.getActif()) {
            throw new InvalidCredentialsException("Ce compte est désactivé");
        }

        // 4. Vérifier le mot de passe
        if (!passwordEncoder.matches(motDePasse, user.getMotDePasse())) {
            throw new InvalidCredentialsException();
        }

        // 5. Retourner l'utilisateur authentifié
        return user;
    }
}
