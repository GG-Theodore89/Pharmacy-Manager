package com.pharmacie.service;

import com.pharmacie.model.Lot;
import com.pharmacie.model.Vente;
import com.pharmacie.model.VenteLot;
import java.util.List;

public interface StockService {

    int calculerStock(int produitId);

    boolean verifierDisponibilite(int produitId, int qte);

    List<VenteLot> prelever(int produitId, int qte);

    void restituer(Vente v);

    List<Lot> lotsDuProduit(int produitId);
}
