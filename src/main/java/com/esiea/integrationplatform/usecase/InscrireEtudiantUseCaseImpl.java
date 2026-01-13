package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.EvenementNotFoundException;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.in.InscrireEtudiantUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;
import com.esiea.integrationplatform.domain.port.out.EventPublisherPort;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import com.esiea.integrationplatform.domain.service.InscriptionValidator;

import java.time.LocalDateTime;

/**
 * Implémentation du cas d'usage d'inscription d'un étudiant
 * AUCUNE ANNOTATION SPRING dans le use case
 */
public class InscrireEtudiantUseCaseImpl implements InscrireEtudiantUseCase {

    private final InscriptionRepositoryPort inscriptionRepository;
    private final EvenementRepositoryPort evenementRepository;
    private final EventPublisherPort eventPublisher;
    private final InscriptionValidator validator;

    public InscrireEtudiantUseCaseImpl(
            InscriptionRepositoryPort inscriptionRepository,
            EvenementRepositoryPort evenementRepository,
            EventPublisherPort eventPublisher) {
        this.inscriptionRepository = inscriptionRepository;
        this.evenementRepository = evenementRepository;
        this.eventPublisher = eventPublisher;
        this.validator = new InscriptionValidator();
    }

    @Override
    public Inscription execute(Long etudiantId, Long evenementId, String commentaire) {
        // 1. Vérifier que l'événement existe
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new EvenementNotFoundException(evenementId));

        // 2. Vérifier que l'étudiant n'est pas déjà inscrit
        if (inscriptionRepository.existsByEtudiantIdAndEvenementId(etudiantId, evenementId)) {
            throw new IllegalArgumentException(
                    "L'étudiant est déjà inscrit à cet événement"
            );
        }

        // 3. Créer l'inscription
        Inscription inscription = new Inscription();
        inscription.setEtudiantId(etudiantId);
        inscription.setEvenementId(evenementId);
        inscription.setCommentaire(commentaire);
        inscription.setDateInscription(LocalDateTime.now());

        // 4. Déterminer le statut en fonction de la capacité
        String statut = determinerStatut(evenement);
        inscription.setStatut(statut);

        // 5. Valider l'inscription
        validator.validate(inscription);

        // 6. Sauvegarder
        Inscription inscriptionSauvegardee = inscriptionRepository.save(inscription);

        // 7. Publier l'événement Kafka
        eventPublisher.publishInscriptionCreee(
                inscriptionSauvegardee.getId(),
                inscriptionSauvegardee.getEtudiantId(),
                inscriptionSauvegardee.getEvenementId(),
                inscriptionSauvegardee.getStatut()
        );

        return inscriptionSauvegardee;
    }

    /**
     * Détermine le statut de l'inscription en fonction de la capacité de l'événement
     */
    private String determinerStatut(Evenement evenement) {
        // Si pas de capacité max définie, inscription confirmée
        if (evenement.getCapaciteMax() == null) {
            return "CONFIRMEE";
        }

        // Compter les inscriptions confirmées
        long nombreInscritsConfirmes = inscriptionRepository
                .countByEvenementIdAndStatut(evenement.getId(), "CONFIRMEE");

        // Si capacité atteinte, mettre en liste d'attente
        if (nombreInscritsConfirmes >= evenement.getCapaciteMax()) {
            return "EN_ATTENTE";
        }

        return "CONFIRMEE";
    }
}
