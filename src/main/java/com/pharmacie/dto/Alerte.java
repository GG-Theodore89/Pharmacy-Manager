package com.pharmacie.dto;

import com.pharmacie.service.TypeAlerte;

public class Alerte {

    private final TypeAlerte type;
    private final int produitId;
    private final String produitNom;
    private final String message;

    public Alerte(TypeAlerte type, int produitId, String produitNom, String message) {
        this.type = type;
        this.produitId = produitId;
        this.produitNom = produitNom;
        this.message = message;
    }

    public TypeAlerte getType() {
        return type;
    }

    public int getProduitId() {
        return produitId;
    }

    public String getProduitNom() {
        return produitNom;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return type + " — " + produitNom + " : " + message;
    }
}
