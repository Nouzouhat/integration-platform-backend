package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Evenement;

public interface ModifierEvenementUseCase {
    Evenement modifier(Long id, Evenement evenement);
}