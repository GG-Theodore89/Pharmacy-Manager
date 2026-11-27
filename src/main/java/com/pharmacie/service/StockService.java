package com.pharmacie.service;
import com.pharmacie.model.Lot; 
import com.pharmacie.model.Vente;
import com.pharmacie.model.VenteLot;
import java.util.List;  //cvets une interface de l api collections de java, une collection ordonnee d elements peuvent ete doublons

public interface StockService {
    int calculerStock(int produitId);
    boolean verifierDisponibilite(int produitId, int qte);
    List<VenteLot> prelever(int produitId, int qte); //retourne une liste ventelot contenant lid de vente lot et la quantite prelever (pas d  id de lot?)
    void restituer(Vente v); //restituer au lot d origine en cas d annulation
    List<Lot> lotsDuProduit(int produitId);
}
