package com.pharmacie.dto;

import java.math.BigDecimal;

public class ProduitForm {

    private final String type;
    private final String nom;
    private final String description;
    private final BigDecimal prixVente;
    private final int seuilAlerte;
    private final boolean actif;
    private final boolean surOrdonnance;

    public ProduitForm(String type, String nom, String description,
                       BigDecimal prixVente, int seuilAlerte,
                       boolean actif, boolean surOrdonnance) {
        this.type = type;
        this.nom = nom;
        this.description = description;
        this.prixVente = prixVente;
        this.seuilAlerte = seuilAlerte;
        this.actif = actif;
        this.surOrdonnance = surOrdonnance;
    }

    public ProduitForm(String type, String nom, String description,
                       BigDecimal prixVente, int seuilAlerte) {
        this(type, nom, description, prixVente, seuilAlerte, true, false);
    }

    public String getType() {
        return type;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrixVente() {
        return prixVente;
    }

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public boolean isActif() {
        return actif;
    }

    public boolean isSurOrdonnance() {
        return surOrdonnance;
    }
}
