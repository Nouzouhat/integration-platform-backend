package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.UserAlreadyExistsException;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.CreerCompteUseCase;
import com.esiea.integrationplatform.domain.port.out.PasswordEncoderPort;
import com.esiea.integrationplatform.domain.port.out.UserRepositoryPort;
import com.esiea.integrationplatform.domain.service.UserValidator;

import java.time.LocalDateTime;

/**
 * Implémentation du use case : Créer un compte utilisateur
 */
public class CreerCompteUseCaseImpl implements CreerCompteUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public CreerCompteUseCaseImpl(UserRepositoryPort userRepository, 
                                  PasswordEncoderPort passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(String email, String motDePasse, String nom, String prenom,
                       String promotion, String numeroEtudiant, String filiere) {
        
        // 1. Validation des données
        UserValidator.validateForCreation(email, motDePasse, nom, prenom);

        // 2. Vérifier que l'email n'existe pas déjà
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        // 3. Hasher le mot de passe
        String motDePasseHash = passwordEncoder.encode(motDePasse);

        // 4. Créer l'utilisateur avec le rôle ETUDIANT par défaut
        User user = new User();
        user.setEmail(email.toLowerCase().trim());
        user.setMotDePasse(motDePasseHash);
        user.setNom(nom.trim());
        user.setPrenom(prenom.trim());
        user.setRole("ETUDIANT"); // Par défaut
        user.setActif(true);
        user.setDateCreation(LocalDateTime.now());
        
        // Champs optionnels
        if (promotion != null && !promotion.trim().isEmpty()) {
            user.setPromotion(promotion.trim());
        }
        if (numeroEtudiant != null && !numeroEtudiant.trim().isEmpty()) {
            user.setNumeroEtudiant(numeroEtudiant.trim());
        }
        if (filiere != null && !filiere.trim().isEmpty()) {
            user.setFiliere(filiere.trim());
        }

        // 5. Sauvegarder
        return userRepository.save(user);
    }
}
