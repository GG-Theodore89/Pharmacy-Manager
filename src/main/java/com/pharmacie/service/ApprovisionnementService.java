package com.pharmacie.service;

import com.pharmacie.dto.ApproForm;
import com.pharmacie.model.Approvisionnement;
import java.util.List;

public interface ApprovisionnementService {

    Approvisionnement enregistrer(ApproForm f, int userId);

    List<Approvisionnement> historique();
}
