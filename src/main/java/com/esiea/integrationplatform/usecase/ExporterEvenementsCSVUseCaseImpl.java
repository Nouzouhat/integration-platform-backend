package com.esiea.integrationplatform.usecase;

import com.esiea.integrationplatform.domain.model.Evenement;
import com.esiea.integrationplatform.domain.port.in.ExporterEvenementsCSVUseCase;
import com.esiea.integrationplatform.domain.port.out.EvenementRepositoryPort;

import java.util.List;

public class ExporterEvenementsCSVUseCaseImpl implements ExporterEvenementsCSVUseCase {

    private final EvenementRepositoryPort evenementRepository;

    public ExporterEvenementsCSVUseCaseImpl(EvenementRepositoryPort evenementRepository) {
        this.evenementRepository = evenementRepository;
    }

    @Override
    public String exporterEnCSV() {
        List<Evenement> evenements = evenementRepository.findAll();

        // Construire le CSV
        StringBuilder csv = new StringBuilder();
        csv.append("ID,Titre,Description,Date Début,Date Fin,Lieu,Capacité Max,Type,Statut\n");

        for (Evenement evt : evenements) {
            csv.append(evt.getId()).append(",")
                    .append(escapeCSV(evt.getTitre())).append(",")
                    .append(escapeCSV(evt.getDescription())).append(",")
                    .append(evt.getDateDebut()).append(",")
                    .append(evt.getDateFin()).append(",")
                    .append(escapeCSV(evt.getLieu())).append(",")
                    .append(evt.getCapaciteMax()).append(",")
                    .append(evt.getTypeEvenement()).append(",")
                    .append(evt.getStatut()).append("\n");
        }

        return csv.toString();
    }

    private String escapeCSV(String value) {
        if (value == null) return "";
        // Échapper les guillemets et virgules
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}