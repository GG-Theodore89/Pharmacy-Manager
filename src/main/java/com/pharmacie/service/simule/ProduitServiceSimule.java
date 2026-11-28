package com.pharmacie.service.simule;

import com.pharmacie.service.*;
import com.pharmacie.dto.ProduitForm;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Produit;
import java.util.List;

public class ProduitServiceSimule implements ProduitService {

    @Override
    public Produit ajouter(ProduitForm f) {
        throw new PharmacieException("Le service ProduitServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public Produit modifier(int id, ProduitForm f) {
        throw new PharmacieException("Le service ProduitServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public void desactiver(int id) {
        throw new PharmacieException("Le service ProduitServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public List<Produit> rechercher(String texte) {
        throw new PharmacieException("Le service ProduitServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public void definirSeuil(int id, int seuil) {
        throw new PharmacieException("Le service ProduitServiceSimule n'est pas encore implémenté.");
    }
}
