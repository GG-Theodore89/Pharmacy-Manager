package com.pharmacie.model;

import java.math.BigDecimal;

public class Medicament extends Produit {
    private boolean surOrdonnance;

    //Constructeur sans ID
    public Medicament(String nom, String description, BigDecimal prixVente, int seuilAlerte, boolean surOrdonnance){
        super(nom, description, prixVente, seuilAlerte);
        setSurOrdonnance(surOrdonnance);
    }

    //Constructeur avec ID
    public Medicament(int id, String nom, String description, BigDecimal prixVente, int seuilAlerte, boolean actif, boolean surOrdonnance){
        super(id, nom, description, prixVente, seuilAlerte, actif);
        setSurOrdonnance(surOrdonnance);
    }
    //getters
    public boolean isSurOrdonnance(){
        return surOrdonnance;
    }

    //setters
    final public void setSurOrdonnance(boolean surOrdonnance) {
        this.surOrdonnance = surOrdonnance;
    }
}
