package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.EvenementDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.EvenementRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.EvenementResponseDTO;
import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.CreerEvenementUseCase;
import com.esiea.integrationplatform.domain.port.in.ListerEvenementsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour les événements
 */
@RestController
@RequestMapping("/evenements")
@Tag(name = "Événements", description = "API de gestion des événements de la semaine d'intégration")
public class EvenementController {

    private final CreerEvenementUseCase creerEvenementUseCase;
    private final ListerEvenementsUseCase listerEvenementsUseCase;

    public EvenementController(
            CreerEvenementUseCase creerEvenementUseCase,
            ListerEvenementsUseCase listerEvenementsUseCase) {
        this.creerEvenementUseCase = creerEvenementUseCase;
        this.listerEvenementsUseCase = listerEvenementsUseCase;
    }

    /**
     * Créer un nouvel événement
     */
    @PostMapping
    @Operation(summary = "Créer un événement",
            description = "Crée un nouvel événement pour la semaine d'intégration")
    public ResponseEntity<EvenementResponseDTO> creerEvenement(
            @RequestBody EvenementRequestDTO requestDTO) {

        // Convertir le DTO en entité métier
        Evenement evenement = EvenementDtoMapper.toDomain(requestDTO);

        // Exécuter le use case
        Evenement evenementCree = creerEvenementUseCase.execute(evenement);

        // Convertir le résultat en DTO de réponse
        EvenementResponseDTO responseDTO = EvenementDtoMapper.toResponseDto(evenementCree);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Lister tous les événements
     */
    @GetMapping
    @Operation(summary = "Lister les événements",
            description = "Récupère la liste de tous les événements")
    public ResponseEntity<List<EvenementResponseDTO>> listerEvenements() {

        // Exécuter le use case
        List<Evenement> evenements = listerEvenementsUseCase.execute();

        // Convertir la liste en DTOs
        List<EvenementResponseDTO> responseDTOs = evenements.stream()
                .map(EvenementDtoMapper::toResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDTOs);
    }
}