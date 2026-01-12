package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsqu'une inscription n'est pas trouvée
 */
public class InscriptionNotFoundException extends RuntimeException {

    public InscriptionNotFoundException(Long id) {
        super("Inscription non trouvée avec l'ID : " + id);
    }

    public InscriptionNotFoundException(String message) {
        super(message);
    }
}
