package com.pharmacie.service.simule;

import com.pharmacie.service.*;
import com.pharmacie.dto.Alerte;
import com.pharmacie.exception.PharmacieException;
import java.util.List;

public class AlerteServiceSimule implements AlerteService {

    @Override
    public List<Alerte> consulter(TypeAlerte type) {
        throw new PharmacieException("Le service AlerteServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public List<Alerte> toutesLesAlertes() {
        throw new PharmacieException("Le service AlerteServiceSimule n'est pas encore implémenté.");
    }
}
