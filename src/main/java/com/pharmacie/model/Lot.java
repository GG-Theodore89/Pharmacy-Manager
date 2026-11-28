package com.pharmacie.model;

import java.time.LocalDate;

public class Lot {

    private final int id;
    private final int produitId;
    private final String numeroLot;
    private final LocalDate dateReception;
    private final LocalDate datePeremption;
    private final int quantiteInitiale;
    private int quantiteDisponible;

    public Lot(int id, int produitId, String numeroLot,
               LocalDate dateReception, LocalDate datePeremption,
               int quantiteInitiale, int quantiteDisponible) {
        this.id = id;
        this.produitId = produitId;
        this.numeroLot = numeroLot;
        this.dateReception = dateReception;
        this.datePeremption = datePeremption;
        this.quantiteInitiale = quantiteInitiale;
        this.quantiteDisponible = quantiteDisponible;
    }

    public int getId() {
        return id;
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

    public int getQuantiteInitiale() {
        return quantiteInitiale;
    }

    public int getQuantiteDisponible() {
        return quantiteDisponible;
    }

    public void setQuantiteDisponible(int quantiteDisponible) {
        this.quantiteDisponible = quantiteDisponible;
    }

    @Override
    public String toString() {
        return numeroLot + " — " + quantiteDisponible + " restant(s)";
    }
}
