package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.exception.InscriptionNotFoundException;
import com.esiea.integrationplatform.domain.model.Inscription;
import com.esiea.integrationplatform.domain.port.out.InscriptionRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - AnnulerInscriptionUseCase")
class AnnulerInscriptionUseCaseImplTest {

    @Mock
    private InscriptionRepositoryPort inscriptionRepository;

    private AnnulerInscriptionUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new AnnulerInscriptionUseCaseImpl(inscriptionRepository);
    }

    @Test
    @DisplayName("Devrait annuler une inscription existante")
    void shouldCancelExistingInscription() {
        // Given
        Long inscriptionId = 1L;
        Inscription inscription = createInscription(inscriptionId);
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.of(inscription));

        // When
        useCase.execute(inscriptionId);

        // Then
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository).deleteById(inscriptionId);
    }

    @Test
    @DisplayName("Devrait lever une exception si l'inscription n'existe pas")
    void shouldThrowExceptionWhenInscriptionNotFound() {
        // Given
        Long inscriptionId = 999L;
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> useCase.execute(inscriptionId))
                .isInstanceOf(InscriptionNotFoundException.class);
        
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Devrait vérifier l'existence avant de supprimer")
    void shouldVerifyExistenceBeforeDeleting() {
        // Given
        Long inscriptionId = 1L;
        Inscription inscription = createInscription(inscriptionId);
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.of(inscription));

        // When
        useCase.execute(inscriptionId);

        // Then
        verify(inscriptionRepository, times(1)).findById(inscriptionId);
        verify(inscriptionRepository, times(1)).deleteById(inscriptionId);
    }

    private Inscription createInscription(Long id) {
        Inscription inscription = new Inscription();
        inscription.setId(id);
        inscription.setEtudiantId(1L);
        inscription.setEvenementId(1L);
        inscription.setStatut("CONFIRMEE");
        inscription.setCommentaire("Test");
        inscription.setDateInscription(LocalDateTime.now());
        return inscription;
    }
}
