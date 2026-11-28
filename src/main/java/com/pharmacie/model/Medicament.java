package com.pharmacie.model;

import java.math.BigDecimal;

public class Medicament extends Produit {

    public Medicament(int id, String nom, String description,
                      BigDecimal prixVente, int seuilAlerte,
                      boolean actif, boolean surOrdonnance) {
        super(id, "MEDICAMENT", nom, description, prixVente,
              seuilAlerte, actif, surOrdonnance);
    }
}
