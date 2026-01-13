package com.esiea.integrationplatform.domain.service;

import com.esiea.integrationplatform.domain.model.Evenement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tests unitaires - EvenementValidator")
class EvenementValidatorTest {

    private EvenementValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EvenementValidator();
    }

    @Test
    @DisplayName("Devrait valider un événement correct")
    void shouldValidateCorrectEvenement() {
        // Given
        Evenement evenement = createValidEvenement();

        // When & Then
        assertThatCode(() -> validator.validate(evenement))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Devrait lever une exception si le titre est null")
    void shouldThrowExceptionWhenTitreIsNull() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setTitre(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("titre");
    }

    @Test
    @DisplayName("Devrait lever une exception si le titre est vide")
    void shouldThrowExceptionWhenTitreIsEmpty() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setTitre("   ");

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("titre");
    }

    @Test
    @DisplayName("Devrait lever une exception si la date de début est null")
    void shouldThrowExceptionWhenDateDebutIsNull() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setDateDebut(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("date de début");
    }

    @Test
    @DisplayName("Devrait lever une exception si la date de fin est null")
    void shouldThrowExceptionWhenDateFinIsNull() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setDateFin(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("date de fin");
    }

    @Test
    @DisplayName("Devrait lever une exception si la date de fin est avant la date de début")
    void shouldThrowExceptionWhenDateFinBeforeDateDebut() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setDateDebut(LocalDateTime.now().plusDays(2));
        evenement.setDateFin(LocalDateTime.now().plusDays(1));

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("date de fin")
                .hasMessageContaining("postérieure");
    }

    @Test
    @DisplayName("Devrait lever une exception si le lieu est null")
    void shouldThrowExceptionWhenLieuIsNull() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setLieu(null);

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("lieu");
    }

    @Test
    @DisplayName("Devrait accepter une capacité max null")
    void shouldAcceptNullCapaciteMax() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setCapaciteMax(null);

        // When & Then
        assertThatCode(() -> validator.validate(evenement))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Devrait lever une exception si la capacité max est négative")
    void shouldThrowExceptionWhenCapaciteMaxIsNegative() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setCapaciteMax(-1);

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("capacité");
    }

    @Test
    @DisplayName("Devrait lever une exception si la capacité max est zéro")
    void shouldThrowExceptionWhenCapaciteMaxIsZero() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setCapaciteMax(0);

        // When & Then
        assertThatThrownBy(() -> validator.validate(evenement))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("capacité");
    }

    @Test
    @DisplayName("Devrait accepter une description null")
    void shouldAcceptNullDescription() {
        // Given
        Evenement evenement = createValidEvenement();
        evenement.setDescription(null);

        // When & Then
        assertThatCode(() -> validator.validate(evenement))
                .doesNotThrowAnyException();
    }

    private Evenement createValidEvenement() {
        Evenement evenement = new Evenement();
        evenement.setTitre("Événement Test");
        evenement.setDescription("Description de test");
        evenement.setDateDebut(LocalDateTime.now().plusDays(1));
        evenement.setDateFin(LocalDateTime.now().plusDays(2));
        evenement.setLieu("Salle A");
        evenement.setCapaciteMax(50);
        return evenement;
    }
}
