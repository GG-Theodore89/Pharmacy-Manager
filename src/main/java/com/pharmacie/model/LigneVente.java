package com.pharmacie.model;

import java.math.BigDecimal;

public class LigneVente {

    private final int id;
    private final int produitId;
    private final int quantite;
    private final BigDecimal prixUnitaire;

    public LigneVente(int id, int produitId, int quantite, BigDecimal prixUnitaire) {
        this.id = id;
        this.produitId = produitId;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public int getId() {
        return id;
    }

    public int getProduitId() {
        return produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }
}
