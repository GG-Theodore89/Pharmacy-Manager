package com.pharmacie.dto;

import java.time.LocalDate;

public class ApproForm {

    private final int produitId;
    private final String numeroLot;
    private final LocalDate dateReception;
    private final LocalDate datePeremption;
    private final int quantiteRecue;
    private final String fournisseur;

    public ApproForm(int produitId, String numeroLot,
                     LocalDate dateReception, LocalDate datePeremption,
                     int quantiteRecue, String fournisseur) {
        this.produitId = produitId;
        this.numeroLot = numeroLot;
        this.dateReception = dateReception;
        this.datePeremption = datePeremption;
        this.quantiteRecue = quantiteRecue;
        this.fournisseur = fournisseur;
    }

    public int getProduitId() {
        return produitId;
    }

    public String getNumeroLot() {
        return numeroLot;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public LocalDate getDatePeremption() {
        return datePeremption;
    }

    public int getQuantiteRecue() {
        return quantiteRecue;
    }

    public String getFournisseur() {
        return fournisseur;
    }
}
