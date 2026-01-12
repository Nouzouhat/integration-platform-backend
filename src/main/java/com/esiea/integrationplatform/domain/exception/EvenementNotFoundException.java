package com.esiea.integrationplatform.domain.exception;

/**
 * Exception levée lorsqu'un événement n'est pas trouvé
 */
public class EvenementNotFoundException extends RuntimeException {

    public EvenementNotFoundException(Long id) {
        super("Événement non trouvé avec l'ID : " + id);
    }

    public EvenementNotFoundException(String message) {
        super(message);
    }
}
