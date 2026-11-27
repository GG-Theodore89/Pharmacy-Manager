package com.pharmacie.service;
//définition du contrat fonctionnel

import com.pharmacie.dto.ProduitForm;
import com.pharmacie.model.Produit;

import java.util.List;

public interface ProduitService {
    Produit ajouter(ProduitForm f);
    Produit modifier(int id, ProduitForm f);
    void desactiver(int id);
    void activer(int id);
    List<Produit> rechercher(String texte);
    void definirSeuil(int id,int seuil);
}
