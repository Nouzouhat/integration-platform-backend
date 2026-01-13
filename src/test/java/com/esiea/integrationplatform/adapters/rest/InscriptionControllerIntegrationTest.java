package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.InscriptionRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.ModifierInscriptionRequestDTO;
import com.esiea.integrationplatform.config.TestSecurityConfig;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.infrastructure.persistence.repository.JpaEvenementRepository;
import com.esiea.integrationplatform.infrastructure.persistence.repository.JpaInscriptionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("Tests d'intégration - InscriptionController")
class InscriptionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaInscriptionRepository inscriptionRepository;

    @Autowired
    private JpaEvenementRepository evenementRepository;

    private Long evenementId;

    @BeforeEach
    void setUp() {
        inscriptionRepository.deleteAll();
        evenementRepository.deleteAll();
        
        // Créer un événement de test
        evenementId = createTestEvenement();
    }

    @Test
    @DisplayName("POST /inscriptions - Devrait créer une inscription")
    void shouldCreateInscription() throws Exception {
        // Given
        InscriptionRequestDTO requestDTO = new InscriptionRequestDTO();
        requestDTO.setEtudiantId(1L);
        requestDTO.setEvenementId(evenementId);
        requestDTO.setCommentaire("Je souhaite participer");

        // When & Then
        mockMvc.perform(post("/inscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.etudiantId").value(1))
                .andExpect(jsonPath("$.evenementId").value(evenementId))
                .andExpect(jsonPath("$.statut").value("CONFIRMEE"))
                .andExpect(jsonPath("$.commentaire").value("Je souhaite participer"))
                .andExpect(jsonPath("$.dateInscription").exists());
    }

    @Test
    @DisplayName("GET /inscriptions/{id} - Devrait récupérer une inscription")
    void shouldGetInscriptionById() throws Exception {
        // Given
        Long inscriptionId = createTestInscription(1L, evenementId);

        // When & Then
        mockMvc.perform(get("/inscriptions/{id}", inscriptionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(inscriptionId))
                .andExpect(jsonPath("$.etudiantId").value(1))
                .andExpect(jsonPath("$.statut").value("CONFIRMEE"));
    }

    @Test
    @DisplayName("GET /inscriptions/{id} - Devrait retourner 404 si non trouvée")
    void shouldReturn404WhenInscriptionNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/inscriptions/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /inscriptions?etudiantId={id} - Devrait récupérer les inscriptions d'un étudiant")
    void shouldGetInscriptionsByEtudiant() throws Exception {
        // Given
        Long evenementId2 = createTestEvenement(); // Créer un deuxième événement
        createTestInscription(1L, evenementId);
        createTestInscription(1L, evenementId2); // Même étudiant, autre événement

        // When & Then
        mockMvc.perform(get("/inscriptions")
                        .param("etudiantId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Exactement 2 inscriptions
                .andExpect(jsonPath("$[0].etudiantId").value(1));
    }

    @Test
    @DisplayName("PUT /inscriptions/{id} - Devrait modifier une inscription")
    void shouldUpdateInscription() throws Exception {
        // Given
        Long inscriptionId = createTestInscription(1L, evenementId);
        
        ModifierInscriptionRequestDTO requestDTO = new ModifierInscriptionRequestDTO();
        requestDTO.setStatut("ANNULEE");
        requestDTO.setCommentaire("Changement de plans");

        // When & Then
        mockMvc.perform(put("/inscriptions/{id}", inscriptionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(inscriptionId))
                .andExpect(jsonPath("$.statut").value("ANNULEE"))
                .andExpect(jsonPath("$.commentaire").value("Changement de plans"));
    }

    @Test
    @DisplayName("PATCH /inscriptions/{id} - Devrait modifier partiellement une inscription")
    void shouldPartiallyUpdateInscription() throws Exception {
        // Given
        Long inscriptionId = createTestInscription(1L, evenementId);
        
        ModifierInscriptionRequestDTO requestDTO = new ModifierInscriptionRequestDTO();
        requestDTO.setStatut("EN_ATTENTE");
        // Pas de commentaire

        // When & Then
        mockMvc.perform(patch("/inscriptions/{id}", inscriptionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut").value("EN_ATTENTE"));
    }

    @Test
    @DisplayName("DELETE /inscriptions/{id} - Devrait supprimer une inscription")
    void shouldDeleteInscription() throws Exception {
        // Given
        Long inscriptionId = createTestInscription(1L, evenementId);

        // When & Then
        mockMvc.perform(delete("/inscriptions/{id}", inscriptionId))
                .andExpect(status().isNoContent());

        // Vérifier que l'inscription a été supprimée
        mockMvc.perform(get("/inscriptions/{id}", inscriptionId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /inscriptions - Devrait retourner 400 si l'étudiant est déjà inscrit")
    void shouldReturn400WhenStudentAlreadyRegistered() throws Exception {
        // Given
        createTestInscription(1L, evenementId);
        
        InscriptionRequestDTO requestDTO = new InscriptionRequestDTO();
        requestDTO.setEtudiantId(1L);
        requestDTO.setEvenementId(evenementId);

        // When & Then
        mockMvc.perform(post("/inscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    private Long createTestEvenement() {
        com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity entity = 
            new com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity();
        entity.setTitre("Événement Test");
        entity.setDescription("Description");
        entity.setDateDebut(LocalDateTime.now().plusDays(1));
        entity.setDateFin(LocalDateTime.now().plusDays(2));
        entity.setLieu("Salle A");
        entity.setCapaciteMax(50);
        return evenementRepository.save(entity).getId();
    }

    private Long createTestInscription(Long etudiantId, Long evenementId) {
        com.esiea.integrationplatform.infrastructure.persistence.entity.InscriptionEntity entity = 
            new com.esiea.integrationplatform.infrastructure.persistence.entity.InscriptionEntity();
        entity.setEtudiantId(etudiantId);
        entity.setEvenementId(evenementId);
        entity.setStatut("CONFIRMEE");
        entity.setCommentaire("Test");
        entity.setDateInscription(LocalDateTime.now());
        return inscriptionRepository.save(entity).getId();
    }
}
