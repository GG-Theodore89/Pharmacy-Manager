package com.pharmacie.dao;

import com.pharmacie.model.Produit;
import java.util.List;

public interface ProduitDao {

    // Enregistrer un nouveau produit dans la BD
    Produit enregistrer(Produit produit);

    // Mettre à jour un produit existant
    void mettreAJour(Produit produit);

    // Rechercher un produit par son identifiant
    Produit trouverParId(int id);

    // Rechercher des produits par texte
    // actifsSeulement = true : uniquement les produits actifs
    // actifsSeulement = false : tous les produitsk
    List<Produit> rechercher(String texte, boolean actifsSeulement);

    // Récupérer tous les produits, actifs et inactifs
    List<Produit> listerTous();
}
