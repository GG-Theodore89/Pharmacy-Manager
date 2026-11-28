package com.pharmacie.model;

import java.math.BigDecimal;

public class Parapharmacie extends Produit {

    public Parapharmacie(int id, String nom, String description,
                         BigDecimal prixVente, int seuilAlerte,
                         boolean actif, boolean surOrdonnance) {
        super(id, "PARAPHARMACIE", nom, description, prixVente,
              seuilAlerte, actif, surOrdonnance);
    }
}
