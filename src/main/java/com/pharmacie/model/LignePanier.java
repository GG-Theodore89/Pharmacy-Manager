```java
package com.pharmacie.model;

public class LignePanier {

    private int produit_id; // Identifiant du produit sélectionné
    private String nom; // Nom du produit affiché dans le panier
    private int quantite; // Quantité souhaitée
    private int prix_unitaire_cents; // Prix unitaire au moment de la préparation de la vente

    public LignePanier(int produit_id, String nom, int quantite, int prix_unitaire_cents) {
        this.produit_id = produit_id;
        this.nom = nom;
        this.quantite = quantite;
        this.prix_unitaire_cents = prix_unitaire_cents;
    }

    // Calcul du sous-total de la ligne
    public int getSousTotalCents() {
        return quantite * prix_unitaire_cents;
    }

    // Getters et Setters
    public int getProduitId() {
        return produit_id;
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrixUnitaireCents() {
        return prix_unitaire_cents;
    }
}
```