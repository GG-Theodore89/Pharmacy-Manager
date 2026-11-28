package com.pharmacie.model;

import java.math.BigDecimal;

public abstract class Produit {

    private final int id;
    private final String type;
    private String nom;
    private String description;
    private BigDecimal prixVente;
    private int seuilAlerte;
    private boolean actif;
    private boolean surOrdonnance;

    public Produit(int id, String type, String nom, String description,
                   BigDecimal prixVente, int seuilAlerte,
                   boolean actif, boolean surOrdonnance) {
        this.id = id;
        this.type = type;
        this.nom = nom;
        this.description = description;
        this.prixVente = prixVente;
        this.seuilAlerte = seuilAlerte;
        this.actif = actif;
        this.surOrdonnance = surOrdonnance;
    }

    public int getId() {
        return id;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }

    public void setSeuilAlerte(int seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public void setSurOrdonnance(boolean surOrdonnance) {
        this.surOrdonnance = surOrdonnance;
    }

    @Override
    public String toString() {
        return nom;
    }
}
