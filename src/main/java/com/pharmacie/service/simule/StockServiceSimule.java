package com.pharmacie.service.simule;

import com.pharmacie.service.*;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Lot;
import com.pharmacie.model.Vente;
import com.pharmacie.model.VenteLot;
import java.util.List;

public class StockServiceSimule implements StockService {

    @Override
    public int calculerStock(int produitId) {
        throw new PharmacieException("Le service StockServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public boolean verifierDisponibilite(int produitId, int qte) {
        throw new PharmacieException("Le service StockServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public List<VenteLot> prelever(int produitId, int qte) {
        throw new PharmacieException("Le service StockServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public void restituer(Vente v) {
        throw new PharmacieException("Le service StockServiceSimule n'est pas encore implémenté.");
    }

    @Override
    public List<Lot> lotsDuProduit(int produitId) {
        throw new PharmacieException("Le service StockServiceSimule n'est pas encore implémenté.");
    }
}
