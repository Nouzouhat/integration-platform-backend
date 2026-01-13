package com.esiea.integrationplatform.infrastructure.persistence.repository;

import com.esiea.integrationplatform.config.TestSecurityConfig;
import com.esiea.integrationplatform.infrastructure.persistence.entity.InscriptionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("Tests d'intégration - JpaInscriptionRepository")
class JpaInscriptionRepositoryTest {

    @Autowired
    private JpaInscriptionRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Devrait sauvegarder et récupérer une inscription")
    void shouldSaveAndFindInscription() {
        // Given
        InscriptionEntity inscription = createInscription(1L, 1L, "CONFIRMEE");

        // When
        InscriptionEntity saved = repository.save(inscription);
        InscriptionEntity found = repository.findById(saved.getId()).orElse(null);

        // Then
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getEtudiantId()).isEqualTo(1L);
        assertThat(found.getEvenementId()).isEqualTo(1L);
        assertThat(found.getStatut()).isEqualTo("CONFIRMEE");
    }

    @Test
    @DisplayName("Devrait trouver les inscriptions par étudiant")
    void shouldFindByEtudiantId() {
        // Given
        repository.save(createInscription(1L, 1L, "CONFIRMEE"));
        repository.save(createInscription(1L, 2L, "CONFIRMEE"));
        repository.save(createInscription(2L, 1L, "CONFIRMEE"));

        // When
        List<InscriptionEntity> inscriptions = repository.findByEtudiantId(1L);

        // Then
        assertThat(inscriptions).hasSize(2);
        assertThat(inscriptions).allMatch(i -> i.getEtudiantId().equals(1L));
    }

    @Test
    @DisplayName("Devrait trouver les inscriptions par événement")
    void shouldFindByEvenementId() {
        // Given
        repository.save(createInscription(1L, 1L, "CONFIRMEE"));
        repository.save(createInscription(2L, 1L, "CONFIRMEE"));
        repository.save(createInscription(3L, 2L, "CONFIRMEE"));

        // When
        List<InscriptionEntity> inscriptions = repository.findByEvenementId(1L);

        // Then
        assertThat(inscriptions).hasSize(2);
        assertThat(inscriptions).allMatch(i -> i.getEvenementId().equals(1L));
    }

    @Test
    @DisplayName("Devrait vérifier l'existence d'une inscription")
    void shouldCheckExistenceByEtudiantAndEvenement() {
        // Given
        repository.save(createInscription(1L, 1L, "CONFIRMEE"));

        // When
        boolean exists = repository.existsByEtudiantIdAndEvenementId(1L, 1L);
        boolean notExists = repository.existsByEtudiantIdAndEvenementId(1L, 2L);

        // Then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("Devrait compter les inscriptions par événement et statut")
    void shouldCountByEvenementIdAndStatut() {
        // Given
        repository.save(createInscription(1L, 1L, "CONFIRMEE"));
        repository.save(createInscription(2L, 1L, "CONFIRMEE"));
        repository.save(createInscription(3L, 1L, "EN_ATTENTE"));
        repository.save(createInscription(4L, 2L, "CONFIRMEE"));

        // When
        long countConfirmees = repository.countByEvenementIdAndStatut(1L, "CONFIRMEE");
        long countEnAttente = repository.countByEvenementIdAndStatut(1L, "EN_ATTENTE");

        // Then
        assertThat(countConfirmees).isEqualTo(2);
        assertThat(countEnAttente).isEqualTo(1);
    }

    @Test
    @DisplayName("Devrait supprimer une inscription par ID")
    void shouldDeleteById() {
        // Given
        InscriptionEntity inscription = repository.save(createInscription(1L, 1L, "CONFIRMEE"));
        Long id = inscription.getId();

        // When
        repository.deleteById(id);

        // Then
        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    @DisplayName("Devrait retourner une liste vide si aucune inscription pour un étudiant")
    void shouldReturnEmptyListWhenNoInscriptionsForEtudiant() {
        // When
        List<InscriptionEntity> inscriptions = repository.findByEtudiantId(999L);

        // Then
        assertThat(inscriptions).isEmpty();
    }

    private InscriptionEntity createInscription(Long etudiantId, Long evenementId, String statut) {
        InscriptionEntity inscription = new InscriptionEntity();
        inscription.setEtudiantId(etudiantId);
        inscription.setEvenementId(evenementId);
        inscription.setStatut(statut);
        inscription.setCommentaire("Test");
        inscription.setDateInscription(LocalDateTime.now());
        return inscription;
    }
}
