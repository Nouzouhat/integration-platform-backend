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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - ModifierInscriptionUseCase")
class ModifierInscriptionUseCaseImplTest {

    @Mock
    private InscriptionRepositoryPort inscriptionRepository;

    private ModifierInscriptionUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ModifierInscriptionUseCaseImpl(inscriptionRepository);
    }

    @Test
    @DisplayName("Devrait modifier le statut d'une inscription existante")
    void shouldModifyInscriptionStatus() {
        // Given
        Long inscriptionId = 1L;
        Inscription existingInscription = createInscription(inscriptionId, "CONFIRMEE", "Commentaire initial");
        
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.of(existingInscription));
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Inscription result = useCase.execute(inscriptionId, "ANNULEE", null);

        // Then
        assertThat(result.getStatut()).isEqualTo("ANNULEE");
        assertThat(result.getCommentaire()).isEqualTo("Commentaire initial"); // Inchangé
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository).save(any(Inscription.class));
    }

    @Test
    @DisplayName("Devrait modifier le commentaire d'une inscription existante")
    void shouldModifyInscriptionCommentaire() {
        // Given
        Long inscriptionId = 1L;
        Inscription existingInscription = createInscription(inscriptionId, "CONFIRMEE", "Commentaire initial");
        
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.of(existingInscription));
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Inscription result = useCase.execute(inscriptionId, null, "Nouveau commentaire");

        // Then
        assertThat(result.getStatut()).isEqualTo("CONFIRMEE"); // Inchangé
        assertThat(result.getCommentaire()).isEqualTo("Nouveau commentaire");
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository).save(any(Inscription.class));
    }

    @Test
    @DisplayName("Devrait modifier les deux champs simultanément")
    void shouldModifyBothFields() {
        // Given
        Long inscriptionId = 1L;
        Inscription existingInscription = createInscription(inscriptionId, "CONFIRMEE", "Commentaire initial");
        
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.of(existingInscription));
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Inscription result = useCase.execute(inscriptionId, "EN_ATTENTE", "Mis en liste d'attente");

        // Then
        assertThat(result.getStatut()).isEqualTo("EN_ATTENTE");
        assertThat(result.getCommentaire()).isEqualTo("Mis en liste d'attente");
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository).save(any(Inscription.class));
    }

    @Test
    @DisplayName("Devrait lever une exception si l'inscription n'existe pas")
    void shouldThrowExceptionWhenInscriptionNotFound() {
        // Given
        Long inscriptionId = 999L;
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> useCase.execute(inscriptionId, "ANNULEE", null))
                .isInstanceOf(InscriptionNotFoundException.class);
        
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository, never()).save(any());
    }

    @Test
    @DisplayName("Ne devrait pas modifier si les champs sont null")
    void shouldNotModifyWhenFieldsAreNull() {
        // Given
        Long inscriptionId = 1L;
        Inscription existingInscription = createInscription(inscriptionId, "CONFIRMEE", "Commentaire initial");
        
        when(inscriptionRepository.findById(inscriptionId)).thenReturn(Optional.of(existingInscription));
        when(inscriptionRepository.save(any(Inscription.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Inscription result = useCase.execute(inscriptionId, null, null);

        // Then
        assertThat(result.getStatut()).isEqualTo("CONFIRMEE");
        assertThat(result.getCommentaire()).isEqualTo("Commentaire initial");
        verify(inscriptionRepository).findById(inscriptionId);
        verify(inscriptionRepository).save(any(Inscription.class));
    }

    private Inscription createInscription(Long id, String statut, String commentaire) {
        Inscription inscription = new Inscription();
        inscription.setId(id);
        inscription.setEtudiantId(1L);
        inscription.setEvenementId(1L);
        inscription.setStatut(statut);
        inscription.setCommentaire(commentaire);
        inscription.setDateInscription(LocalDateTime.now());
        return inscription;
    }
}
