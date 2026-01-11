package com.esiea.integrationplatform.domain.model;

import java.time.LocalDateTime;

/**
 * Entité métier représentant un événement de la semaine d'intégration
 * AUCUNE ANNOTATION SPRING/JPA - Pure Java
 */
public class Evenement {

    private Long id;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private Integer capaciteMax;
    private String typeEvenement; // SOIREE, ATELIER, SPORT, CONFERENCE
    private String statut; // BROUILLON, PUBLIE, ANNULE, TERMINE

    // Constructeur vide
    public Evenement() {
    }

    // Constructeur complet
    public Evenement(Long id, String titre, String description,
                     LocalDateTime dateDebut, LocalDateTime dateFin,
                     String lieu, Integer capaciteMax,
                     String typeEvenement, String statut) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.capaciteMax = capaciteMax;
        this.typeEvenement = typeEvenement;
        this.statut = statut;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Integer getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(Integer capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public String getTypeEvenement() {
        return typeEvenement;
    }

    public void setTypeEvenement(String typeEvenement) {
        this.typeEvenement = typeEvenement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", lieu='" + lieu + '\'' +
                ", capaciteMax=" + capaciteMax +
                ", typeEvenement='" + typeEvenement + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}