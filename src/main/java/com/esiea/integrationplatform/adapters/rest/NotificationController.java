package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.CreateNotificationRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.ErrorResponseDTO;
import com.esiea.integrationplatform.adapters.rest.dto.NotificationDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.NotificationResponseDTO;
import com.esiea.integrationplatform.domain.exception.NotificationNotFoundException;
import com.esiea.integrationplatform.domain.model.Notification;
import com.esiea.integrationplatform.domain.port.in.CreerNotificationUseCase;
import com.esiea.integrationplatform.domain.port.in.MarquerNotificationLueUseCase;
import com.esiea.integrationplatform.domain.port.in.ObtenirNotificationsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "API de gestion des notifications")
public class NotificationController {

    private final CreerNotificationUseCase creerNotificationUseCase;
    private final ObtenirNotificationsUseCase obtenirNotificationsUseCase;
    private final MarquerNotificationLueUseCase marquerNotificationLueUseCase;

    public NotificationController(CreerNotificationUseCase creerNotificationUseCase,
                                  ObtenirNotificationsUseCase obtenirNotificationsUseCase,
                                  MarquerNotificationLueUseCase marquerNotificationLueUseCase) {
        this.creerNotificationUseCase = creerNotificationUseCase;
        this.obtenirNotificationsUseCase = obtenirNotificationsUseCase;
        this.marquerNotificationLueUseCase = marquerNotificationLueUseCase;
    }

    /**
     * Créer une nouvelle notification
     */
    @PostMapping
    @Operation(summary = "Créer une notification",
            description = "Crée une nouvelle notification pour un utilisateur et publie un événement Kafka")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification créée avec succès",
                    content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> createNotification(@RequestBody CreateNotificationRequestDTO requestDTO) {
        try {
            // Exécuter le use case
            Notification notification = creerNotificationUseCase.creerNotification(
                    requestDTO.getUserId(),
                    requestDTO.getType(),
                    requestDTO.getTitre(),
                    requestDTO.getMessage(),
                    requestDTO.getEvenementId(),
                    requestDTO.getAction()
            );

            // Convertir en DTO
            NotificationResponseDTO responseDTO = NotificationDtoMapper.toResponseDTO(notification);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Obtenir toutes les notifications d'un utilisateur
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtenir les notifications d'un utilisateur",
            description = "Récupère toutes les notifications d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> getUserNotifications(@PathVariable Long userId) {
        try {
            // Exécuter le use case
            List<Notification> notifications = obtenirNotificationsUseCase.obtenirNotificationsUtilisateur(userId);

            // Convertir en DTOs
            List<NotificationResponseDTO> responseDTOs = notifications.stream()
                    .map(NotificationDtoMapper::toResponseDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responseDTOs);

        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Obtenir les notifications non lues d'un utilisateur
     */
    @GetMapping("/user/{userId}/unread")
    @Operation(summary = "Obtenir les notifications non lues",
            description = "Récupère les notifications non lues d'un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notifications non lues récupérées avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> getUnreadNotifications(@PathVariable Long userId) {
        try {
            // Exécuter le use case
            List<Notification> notifications = obtenirNotificationsUseCase.obtenirNotificationsNonLues(userId);

            // Convertir en DTOs
            List<NotificationResponseDTO> responseDTOs = notifications.stream()
                    .map(NotificationDtoMapper::toResponseDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(responseDTOs);

        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Marquer une notification comme lue
     */
    @PatchMapping("/{notificationId}/read")
    @Operation(summary = "Marquer une notification comme lue",
            description = "Marque une notification comme lue pour un utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification marquée comme lue",
                    content = @Content(schema = @Schema(implementation = NotificationResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Données invalides",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Notification non trouvée",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erreur interne du serveur",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<?> markAsRead(
            @PathVariable Long notificationId,
            @RequestParam Long userId) {
        try {
            // Exécuter le use case
            Notification notification = marquerNotificationLueUseCase.marquerCommeLue(notificationId, userId);

            // Convertir en DTO
            NotificationResponseDTO responseDTO = NotificationDtoMapper.toResponseDTO(notification);

            return ResponseEntity.ok(responseDTO);

        } catch (NotificationNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Not Found");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Bad Request");
            error.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
