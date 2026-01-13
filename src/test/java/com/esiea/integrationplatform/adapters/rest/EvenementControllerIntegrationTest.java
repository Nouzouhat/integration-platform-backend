package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.EvenementRequestDTO;
import com.esiea.integrationplatform.config.TestSecurityConfig;
import com.esiea.integrationplatform.infrastructure.persistence.repository.JpaEvenementRepository;
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
@DisplayName("Tests d'intégration - EvenementController")
class EvenementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JpaEvenementRepository evenementRepository;

    @BeforeEach
    void setUp() {
        evenementRepository.deleteAll();
    }

    @Test
    @DisplayName("POST /evenements - Devrait créer un événement")
    void shouldCreateEvenement() throws Exception {
        // Given
        EvenementRequestDTO requestDTO = new EvenementRequestDTO();
        requestDTO.setTitre("Soirée d'intégration");
        requestDTO.setDescription("Grande soirée pour accueillir les nouveaux étudiants");
        requestDTO.setDateDebut(LocalDateTime.now().plusDays(1));
        requestDTO.setDateFin(LocalDateTime.now().plusDays(1).plusHours(4));
        requestDTO.setLieu("Campus ESIEA");
        requestDTO.setCapaciteMax(100);

        // When & Then
        mockMvc.perform(post("/evenements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titre").value("Soirée d'intégration"))
                .andExpect(jsonPath("$.description").value("Grande soirée pour accueillir les nouveaux étudiants"))
                .andExpect(jsonPath("$.lieu").value("Campus ESIEA"))
                .andExpect(jsonPath("$.capaciteMax").value(100))
                .andExpect(jsonPath("$.dateDebut").exists())
                .andExpect(jsonPath("$.dateFin").exists());
    }

    @Test
    @DisplayName("GET /evenements - Devrait lister tous les événements")
    void shouldListAllEvenements() throws Exception {
        // Given
        createTestEvenement("Événement 1");
        createTestEvenement("Événement 2");
        createTestEvenement("Événement 3");

        // When & Then
        mockMvc.perform(get("/evenements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].titre").exists())
                .andExpect(jsonPath("$[1].titre").exists())
                .andExpect(jsonPath("$[2].titre").exists());
    }

    @Test
    @DisplayName("GET /evenements - Devrait retourner une liste vide si aucun événement")
    void shouldReturnEmptyListWhenNoEvenements() throws Exception {
        // When & Then
        mockMvc.perform(get("/evenements"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("POST /evenements - Devrait retourner 400 si le titre est manquant")
    void shouldReturn400WhenTitreIsMissing() throws Exception {
        // Given
        EvenementRequestDTO requestDTO = new EvenementRequestDTO();
        // Pas de titre
        requestDTO.setDescription("Description");
        requestDTO.setDateDebut(LocalDateTime.now().plusDays(1));
        requestDTO.setDateFin(LocalDateTime.now().plusDays(2));
        requestDTO.setLieu("Salle A");

        // When & Then
        mockMvc.perform(post("/evenements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /evenements - Devrait retourner 400 si la date de fin est avant la date de début")
    void shouldReturn400WhenDateFinBeforeDateDebut() throws Exception {
        // Given
        EvenementRequestDTO requestDTO = new EvenementRequestDTO();
        requestDTO.setTitre("Événement Test");
        requestDTO.setDescription("Description");
        requestDTO.setDateDebut(LocalDateTime.now().plusDays(2));
        requestDTO.setDateFin(LocalDateTime.now().plusDays(1)); // Avant la date de début
        requestDTO.setLieu("Salle A");

        // When & Then
        mockMvc.perform(post("/evenements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /evenements - Devrait accepter un événement sans capacité max")
    void shouldAcceptEvenementWithoutCapaciteMax() throws Exception {
        // Given
        EvenementRequestDTO requestDTO = new EvenementRequestDTO();
        requestDTO.setTitre("Événement sans limite");
        requestDTO.setDescription("Pas de limite de participants");
        requestDTO.setDateDebut(LocalDateTime.now().plusDays(1));
        requestDTO.setDateFin(LocalDateTime.now().plusDays(2));
        requestDTO.setLieu("Amphithéâtre");
        // Pas de capaciteMax

        // When & Then
        mockMvc.perform(post("/evenements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.capaciteMax").doesNotExist());
    }

    private void createTestEvenement(String titre) {
        com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity entity = 
            new com.esiea.integrationplatform.infrastructure.persistence.entity.EvenementEntity();
        entity.setTitre(titre);
        entity.setDescription("Description de " + titre);
        entity.setDateDebut(LocalDateTime.now().plusDays(1));
        entity.setDateFin(LocalDateTime.now().plusDays(2));
        entity.setLieu("Salle Test");
        entity.setCapaciteMax(50);
        evenementRepository.save(entity);
    }
}
