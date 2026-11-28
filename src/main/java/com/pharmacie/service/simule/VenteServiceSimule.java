package com.pharmacie.service.simule;

import com.pharmacie.service.*;
import com.pharmacie.dto.Panier;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Vente;

public class VenteServiceSimule implements VenteService {

    @Override
    public Vente validerVente(Panier p, int userId, Integer clientId) {
        throw new PharmacieException("Le service VenteServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public void annulerVente(int venteId, int userId, String motif) {
        throw new PharmacieException("Le service VenteServiceSimule n'est pas encore implémenté.");
    }
}
