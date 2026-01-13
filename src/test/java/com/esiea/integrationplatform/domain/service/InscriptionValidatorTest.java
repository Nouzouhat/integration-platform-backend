package com.esiea.integrationplatform.domain.service;

import com.esiea.integrationplatform.domain.model.Inscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests unitaires - InscriptionValidator")
class InscriptionValidatorTest {

    private InscriptionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new InscriptionValidator();
    }

    @Test
    @DisplayName("Devrait valider une inscription correcte")
    void shouldValidateCorrectInscription() {
        // Given
        Inscription inscription = createValidInscription();

        // When & Then
        assertThatCode(() -> validator.validate(inscription))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Devrait lever une exception si l'ID étudiant est null")
    void shouldThrowExceptionWhenEtudiantIdIsNull() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setEtudiantId(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(inscription))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("étudiant");
    }

    @Test
    @DisplayName("Devrait lever une exception si l'ID événement est null")
    void shouldThrowExceptionWhenEvenementIdIsNull() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setEvenementId(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(inscription))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("événement");
    }

    @Test
    @DisplayName("Devrait lever une exception si le statut est null")
    void shouldThrowExceptionWhenStatutIsNull() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setStatut(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(inscription))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("statut");
    }

    @Test
    @DisplayName("Devrait lever une exception si le statut est vide")
    void shouldThrowExceptionWhenStatutIsEmpty() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setStatut("");

        // When & Then
        assertThatThrownBy(() -> validator.validate(inscription))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("statut");
    }

    @Test
    @DisplayName("Devrait lever une exception si la date d'inscription est null")
    void shouldThrowExceptionWhenDateInscriptionIsNull() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setDateInscription(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(inscription))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("date");
    }

    @Test
    @DisplayName("Devrait accepter un commentaire null")
    void shouldAcceptNullCommentaire() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setCommentaire(null);

        // When & Then
        assertThatCode(() -> validator.validate(inscription))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Devrait accepter un commentaire vide")
    void shouldAcceptEmptyCommentaire() {
        // Given
        Inscription inscription = createValidInscription();
        inscription.setCommentaire("");

        // When & Then
        assertThatCode(() -> validator.validate(inscription))
                .doesNotThrowAnyException();
    }

    private Inscription createValidInscription() {
        Inscription inscription = new Inscription();
        inscription.setEtudiantId(1L);
        inscription.setEvenementId(1L);
        inscription.setStatut("CONFIRMEE");
        inscription.setDateInscription(LocalDateTime.now());
        inscription.setCommentaire("Test");
        return inscription;
    }
}
