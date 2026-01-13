package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.InscriptionNotFoundException;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.ModifierInscriptionUseCase;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import com.esiea.integrationplatform.domain.service.InscriptionValidator;

/**
 * Implémentation du cas d'usage de modification d'inscription
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class ModifierInscriptionUseCaseImpl implements ModifierInscriptionUseCase {

    private final InscriptionRepositoryPort inscriptionRepository;
    private final InscriptionValidator validator;

    public ModifierInscriptionUseCaseImpl(InscriptionRepositoryPort inscriptionRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.validator = new InscriptionValidator();
    }

    @Override
    public Inscription execute(Long id, String statut, String commentaire) {
        // 1. Récupérer l'inscription existante
        Inscription inscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new InscriptionNotFoundException(id));

        // 2. Mettre à jour les champs si fournis
        if (statut != null && !statut.trim().isEmpty()) {
            inscription.setStatut(statut);
        }

        if (commentaire != null) {
            inscription.setCommentaire(commentaire);
        }

        // 3. Valider l'inscription modifiée
        validator.validate(inscription);

        // 4. Sauvegarder les modifications
        return inscriptionRepository.save(inscription);
    }
}
