package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.*;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.CreerCompteUseCase;
import com.esiea.integrationplatform.domain.port.in.SeConnecterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contrôleur REST pour l'authentification
 * Routes publiques (pas de token requis)
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentification", description = "API d'authentification (register, login)")
public class AuthController {

    private final CreerCompteUseCase creerCompteUseCase;
    private final SeConnecterUseCase seConnecterUseCase;

    public AuthController(CreerCompteUseCase creerCompteUseCase,
                         SeConnecterUseCase seConnecterUseCase) {
        this.creerCompteUseCase = creerCompteUseCase;
        this.seConnecterUseCase = seConnecterUseCase;
    }

    /**
     * Créer un nouveau compte (Register)
     */
    @PostMapping("/register")
    @Operation(summary = "Créer un compte",
            description = "Crée un nouveau compte utilisateur avec le rôle ETUDIANT par défaut")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
        
        // Exécuter le use case
        User user = creerCompteUseCase.execute(
                requestDTO.getEmail(),
                requestDTO.getMotDePasse(),
                requestDTO.getNom(),
                requestDTO.getPrenom(),
                requestDTO.getPromotion(),
                requestDTO.getNumeroEtudiant(),
                requestDTO.getFiliere()
        );

        // Convertir en DTO (sans mot de passe)
        UserResponseDTO responseDTO = UserDtoMapper.toResponseDto(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    /**
     * Se connecter (Login)
     */
    @PostMapping("/login")
    @Operation(summary = "Se connecter",
            description = "Vérifie les identifiants de l'utilisateur. Pour les autres endpoints, utilisez l'authentification Basic Auth.")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
        
        // Exécuter le use case (vérifie les credentials)
        User user = seConnecterUseCase.execute(
                requestDTO.getEmail(),
                requestDTO.getMotDePasse()
        );

        // Créer la réponse
        UserResponseDTO userDTO = UserDtoMapper.toResponseDto(user);
        LoginResponseDTO responseDTO = new LoginResponseDTO(userDTO, "Authentification réussie. Utilisez Basic Auth pour les requêtes suivantes.");

        return ResponseEntity.ok(responseDTO);
    }
}
