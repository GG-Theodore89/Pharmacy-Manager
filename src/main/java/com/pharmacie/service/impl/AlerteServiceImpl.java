package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.dto.Alerte;
import com.pharmacie.exception.PharmacieException;
import java.util.List;

public class AlerteServiceImpl implements AlerteService {

    @Override
    public List<Alerte> consulter(TypeAlerte type) {
        throw new PharmacieException("Le service AlerteServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public List<Alerte> toutesLesAlertes() {
        throw new PharmacieException("Le service AlerteServiceImpl n'est pas encore implémenté.");
    }
}
