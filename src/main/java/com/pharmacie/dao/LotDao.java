
package com.pharmacie.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.pharmacie.model.Lot;

public interface LotDao {

    void enregistrer(Lot lot);

    Optional<Lot> trouverParId(int id);

    List<Lot> trouverParProduit(int produitId);

    List<Lot> trouverDisponiblesFefo(
        int produitId,
        LocalDate aujourdhui
    );

    boolean retirerQuantite(int lotId, int qte);

    void ajouterQuantite(int lotId, int qte);

    List<Lot> listerAvecQuantitePositive();
}
