package com.pharmacie.dto;

import com.pharmacie.model.TypeProduit;

import java.math.BigDecimal;

public class ProduitForm {
    private TypeProduit type;
    // Attributs communs
    private String nom;
    private String description;
    private BigDecimal prixVente;
    private int seuilAlerte;

    // Attribut spécifique à Medicament
    private boolean surOrdonnance;

    // Constructeur privé : utilisé uniquement par les méthodes de fabrication
    private ProduitForm(TypeProduit type, String nom, String description,
                        BigDecimal prixVente, int seuilAlerte, boolean surOrdonnance) {

        if (type == null) {
            throw new IllegalArgumentException(
                    "Le type du produit est obligatoire"
            );
        }

        this.type = type;
        this.nom = nom;
        this.description = description;
        this.prixVente = prixVente;
        this.seuilAlerte = seuilAlerte;
        this.surOrdonnance = surOrdonnance;
    }

    // Méthode de fabrication pour un médicament
    public static ProduitForm pourMedicament(
            String nom, String description,
            BigDecimal prixVente, int seuilAlerte,
            boolean surOrdonnance) {

        return new ProduitForm(
                TypeProduit.MEDICAMENT,
                nom,
                description,
                prixVente,
                seuilAlerte,
                surOrdonnance
        );
    }

    // Méthode de fabrication pour un produit de parapharmacie
    public static ProduitForm pourParapharmacie(
            String nom, String description,
            BigDecimal prixVente, int seuilAlerte) {

        return new ProduitForm(
                TypeProduit.PARAPHARMACIE,
                nom,
                description,
                prixVente,
                seuilAlerte,
                false
        );
    }

    // Getters
    public TypeProduit getType() {
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

    public boolean isSurOrdonnance() {
        return surOrdonnance;
    }
}
