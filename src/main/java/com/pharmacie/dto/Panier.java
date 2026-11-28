package com.pharmacie.dto;

import com.pharmacie.model.Produit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Panier {

    public record LignePanier(Produit produit, int quantite) {}

    private final Map<Integer, Produit> produits = new LinkedHashMap<>();
    private final Map<Integer, Integer> quantites = new LinkedHashMap<>();

    public void ajouterProduit(Produit produit, int qte) {
        if (produit == null || qte <= 0) {
            throw new IllegalArgumentException("Produit et quantité sont obligatoires.");
        }

        produits.put(produit.getId(), produit);
        quantites.merge(produit.getId(), qte, Integer::sum);
    }

    public void retirerProduit(int produitId) {
        quantites.remove(produitId);
        produits.remove(produitId);
    }

    public void modifierQuantite(int produitId, int qte) {
        if (qte <= 0) {
            retirerProduit(produitId);
            return;
        }

        if (produits.containsKey(produitId)) {
            quantites.put(produitId, qte);
        }
    }

    public BigDecimal calculerTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entry : quantites.entrySet()) {
            Produit produit = produits.get(entry.getKey());
            total = total.add(
                produit.getPrixVente().multiply(BigDecimal.valueOf(entry.getValue()))
            );
        }

        return total;
    }

    public List<LignePanier> getLignes() {
        List<LignePanier> lignes = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : quantites.entrySet()) {
            lignes.add(new LignePanier(
                produits.get(entry.getKey()),
                entry.getValue()
            ));
        }

        return lignes;
    }

    public boolean estVide() {
        return quantites.isEmpty();
    }

    public void vider() {
        quantites.clear();
        produits.clear();
    }
}
