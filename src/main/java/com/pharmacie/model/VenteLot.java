package com.pharmacie.model;

public class VenteLot {

    private final int produitId;
    private final int lotId;
    private final String numeroLot;
    private final int quantite;

    public VenteLot(int produitId, int lotId, String numeroLot, int quantite) {
        this.produitId = produitId;
        this.lotId = lotId;
        this.numeroLot = numeroLot;
        this.quantite = quantite;
    }

    public int getProduitId() {
        return produitId;
    }

    public int getLotId() {
        return lotId;
    }

    public String getNumeroLot() {
        return numeroLot;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public String toString() {
        return numeroLot + " x" + quantite;
    }
}
