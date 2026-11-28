package com.pharmacie.service;

import com.pharmacie.dto.Panier;
import com.pharmacie.model.Vente;

public interface VenteService {

    Vente validerVente(Panier p, int userId, Integer clientId);

    void annulerVente(int venteId, int userId, String motif);
}
