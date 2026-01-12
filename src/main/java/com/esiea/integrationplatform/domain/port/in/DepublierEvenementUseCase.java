package com.esiea.integrationplatform.domain.port.in;

import com.esiea.integrationplatform.domain.model.Evenement;

public interface DepublierEvenementUseCase {
    Evenement depublier(Long id);
}