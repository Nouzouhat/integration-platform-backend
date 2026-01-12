package com.esiea.integrationplatform.domain.port.out;

import com.esiea.integrationplatform.domain.model.Evenement;
import java.util.List;
import java.util.Optional;

/**
 * Port de sortie pour la persistance des événements
 * Interface métier - AUCUNE ANNOTATION SPRING
 */
public interface EvenementRepositoryPort {

    /**
     * Sauvegarde un événement (création ou mise à jour)
     * @param evenement l'événement à sauvegarder
     * @return l'événement sauvegardé avec son ID
     */
    Evenement save(Evenement evenement);

    /**
     * Recherche un événement par son ID
     * @param id l'identifiant de l'événement
     * @return Optional contenant l'événement si trouvé
     */
    Optional<Evenement> findById(Long id);

    /**
     * Récupère tous les événements
     * @return liste de tous les événements
     */
    List<Evenement> findAll();

    /**
     * Supprime un événement par son ID
     * @param id l'identifiant de l'événement à supprimer
     */
    void deleteById(Long id);

    /**
     * Vérifie si un événement existe
     * @param id l'identifiant de l'événement
     * @return true si l'événement existe, false sinon
     */
    boolean existsById(Long id);

}