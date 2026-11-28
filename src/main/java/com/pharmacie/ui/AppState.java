package com.pharmacie.ui;

import com.pharmacie.dto.Panier;
import com.pharmacie.model.Vente;

public final class AppState {

    private final Panier panier = new Panier();
    private Vente derniereVente;
    private Vente venteSelectionnee;

    public Panier getPanier() {
        return panier;
    }

    public Vente getDerniereVente() {
        return derniereVente;
    }

    public void setDerniereVente(Vente derniereVente) {
        this.derniereVente = derniereVente;
    }

    public Vente getVenteSelectionnee() {
        return venteSelectionnee;
    }

    public void setVenteSelectionnee(Vente venteSelectionnee) {
        this.venteSelectionnee = venteSelectionnee;
    }
}
