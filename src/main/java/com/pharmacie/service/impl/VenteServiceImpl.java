package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.dto.Panier;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Vente;

public class VenteServiceImpl implements VenteService {

    @Override
    public Vente validerVente(Panier p, int userId, Integer clientId) {
        throw new PharmacieException("Le service VenteServiceImpl n'est pas encore implémenté.");
    }

    @Override
    public void annulerVente(int venteId, int userId, String motif) {
        throw new PharmacieException("Le service VenteServiceImpl n'est pas encore implémenté.");
    }
}
