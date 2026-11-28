package com.pharmacie.model;

import java.time.LocalDate;

public class Approvisionnement {

    private final int id;
    private final int lotId;
    private final int produitId;
    private final String numeroLot;
    private final LocalDate date;
    private final int quantiteRecue;
    private final String fournisseur;

    public Approvisionnement(int id, int lotId, int produitId, String numeroLot,
                             LocalDate date, int quantiteRecue, String fournisseur) {
        this.id = id;
        this.lotId = lotId;
        this.produitId = produitId;
        this.numeroLot = numeroLot;
        this.date = date;
        this.quantiteRecue = quantiteRecue;
        this.fournisseur = fournisseur;
    }

    public int getId() {
        return id;
    }

    public int getLotId() {
        return lotId;
    }

    public int getProduitId() {
        return produitId;
    }

    public String getNumeroLot() {
        return numeroLot;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getQuantiteRecue() {
        return quantiteRecue;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    @Override
    public String toString() {
        return date + " — lot " + numeroLot + " — +" + quantiteRecue;
    }
}
