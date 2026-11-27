package com.pharmacie.model;

import java.util.ArrayList;
import java.util.List;

public class Panier {

    private List<LignePanier> lignes;

    public Panier() {
        this.lignes = new ArrayList<>();
    }

    // Ajouter un produit au panier
    public void ajouterProduit(LignePanier ligne) {
        lignes.add(ligne);  //ajoute cette ligne a la liste  lignes du paniers
    }

    // Supprimer une ligne du panier
    public void supprimerProduit(int produit_id) {
        lignes.removeIf(ligne -> ligne.getProduitId() == produit_id);
    }

    // Modifier la quantité d'un produit
    public void modifierQuantite(int produit_id, int nouvelleQuantite) {
        for (LignePanier ligne : lignes) {
            if (ligne.getProduitId() == produit_id) {
                ligne.setQuantite(nouvelleQuantite);
                break;
            }
        }
    }

    // Calculer le montant total du panier
    public int calculerTotalCents() {
        int total = 0;

        for (LignePanier ligne : lignes) {
            total += ligne.getSousTotalCents();
        }

        return total;
    }

    // Vider le panier
    public void vider() {
        lignes.clear();
    }

    // Récupérer les lignes du panier
    public List<LignePanier> getLignes() {
        return lignes;
    }
}