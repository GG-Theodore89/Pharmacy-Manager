package com.pharmacie.service.simule;

import com.pharmacie.service.*;
import com.pharmacie.dto.ApproForm;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Approvisionnement;
import java.util.List;

public class ApprovisionnementServiceSimule implements ApprovisionnementService {

    @Override
    public Approvisionnement enregistrer(ApproForm f, int userId) {
        throw new PharmacieException("Le service ApprovisionnementServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public List<Approvisionnement> historique() {
        throw new PharmacieException("Le service ApprovisionnementServiceSimule n'est pas encore implémenté.");
    }
}
