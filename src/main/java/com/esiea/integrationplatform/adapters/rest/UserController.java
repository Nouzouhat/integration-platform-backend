package com.esiea.integrationplatform.adapters.rest;

import com.esiea.integrationplatform.adapters.rest.dto.UpdateProfileRequestDTO;
import com.esiea.integrationplatform.adapters.rest.dto.UserDtoMapper;
import com.esiea.integrationplatform.adapters.rest.dto.UserResponseDTO;
import com.esiea.integrationplatform.domain.model.User;
import com.esiea.integrationplatform.domain.port.in.ModifierProfilUseCase;
import com.esiea.integrationplatform.domain.port.in.ObtenirProfilUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/users")
@Tag(name = "Utilisateurs", description = "API de gestion des profils utilisateurs")
public class UserController {

    private final ObtenirProfilUseCase obtenirProfilUseCase;
    private final ModifierProfilUseCase modifierProfilUseCase;

    public UserController(ObtenirProfilUseCase obtenirProfilUseCase,
                         ModifierProfilUseCase modifierProfilUseCase) {
        this.obtenirProfilUseCase = obtenirProfilUseCase;
        this.modifierProfilUseCase = modifierProfilUseCase;
    }

    /**
     * Obtenir un profil utilisateur
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un profil",
            description = "Récupère le profil d'un utilisateur par son ID")
    public ResponseEntity<UserResponseDTO> getProfile(@PathVariable Long id) {
        
        // Exécuter le use case
        User user = obtenirProfilUseCase.execute(id);

        // Convertir en DTO
        UserResponseDTO responseDTO = UserDtoMapper.toResponseDto(user);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Modifier un profil utilisateur
     */
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un profil",
            description = "Modifie le profil d'un utilisateur par son ID")
    public ResponseEntity<UserResponseDTO> updateProfile(
            @PathVariable Long id,
            @RequestBody UpdateProfileRequestDTO requestDTO) {
        
        // Exécuter le use case
        User user = modifierProfilUseCase.execute(
                id,
                requestDTO.getNom(),
                requestDTO.getPrenom(),
                requestDTO.getPromotion(),
                requestDTO.getNumeroEtudiant(),
                requestDTO.getFiliere()
        );

        // Convertir en DTO
        UserResponseDTO responseDTO = UserDtoMapper.toResponseDto(user);

        return ResponseEntity.ok(responseDTO);
    }
}
