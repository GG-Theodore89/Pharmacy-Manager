package com.pharmacie.model;

import java.math.BigDecimal;

public class Parapharmacie extends Produit {

    // Constructeur sans ID pour créer un nouveau produit
    public Parapharmacie(String nom, String description, BigDecimal prixVente, int seuilAlerte) {
        super(nom, description, prixVente, seuilAlerte);
    }

    // Constructeur avec ID pour reconstruire un produit depuis SQLite
    public Parapharmacie(int id, String nom, String description, BigDecimal prixVente, int seuilAlerte, boolean actif) {
        super(id, nom, description, prixVente, seuilAlerte, actif);
    }
}
