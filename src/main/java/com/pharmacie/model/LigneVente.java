package com.pharmacie.model;

import java.math.BigDecimal;

public class LigneVente {

    private int id;
    private int vente_id;
    private int produit_id; 

    // Informations historiques du produit
    //private String nom;

    private int quantite; //prix unitaire applique au moment de la vente , pour ne pas affectees les anciennes ventres par les modifs du prix 
    private BigDecimal prix_unitaire_cents;

    // Constructeur vide utile notamment lors de la recuperation depuis la BDD
    public LigneVente() {
    }

    // Constructeur avec les informations principales
    public LigneVente(int vente_id, int produit_id,int quantite, Integer prix_unitaire_cents) {
        this.vente_id = vente_id;
        this.produit_id = produit_id;
        this.quantite = quantite;
        this.prix_unitaire_cents = prix_unitaire_cents;
    }
    //le sous total est calcule a partir de la quantite et du prix unitaire , pas necessaire de stocker comme attribut 
    
    // Calcul du sous-total
    public long getSousTotal() {
        return prixUnitaire.multiply(BigDecimal.valueOf(quantite));
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVenteId() {
        return venteId;
    }

    public void setVenteId(int venteId) {
        this.venteId = venteId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}