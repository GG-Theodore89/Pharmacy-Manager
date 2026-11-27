package com.pharmacie.service.impl;

import com.pharmacie.dao.LotDao; //couche entre le service et la base de donnees (interface, jamais LotDaoJdbc)
import com.pharmacie.model.LigneVente;
import com.pharmacie.model.Lot;
import com.pharmacie.model.Vente;
import com.pharmacie.model.VenteLot;
import com.pharmacie.service.StockService; //le package ou se trouve l'interface a implementer
import java.time.LocalDate; //classe de l'api date/heure moderne de java
import java.util.ArrayList;
import java.util.List;

public class StockServiceImpl implements StockService {

    // attribut de la CLASSE (une interface n'a pas d'attributs) : le DAO dont le
    // service a besoin
    private final LotDao lotDao;

    // le DAO est fourni de l'exterieur : LotDaoJdbc en production, LotDaoMemoire
    // dans les tests
    public StockServiceImpl(LotDao lotDao) {
        this.lotDao = lotDao;
    }

    // source unique de "aujourd'hui" (RG-60) ; protected pour pouvoir la remplacer
    // dans un test
    protected LocalDate aujourdhui() {
        return LocalDate.now();
    }

    @Override
    public int calculerStock(int produitId) {
        int total = 0;
        // trouverDisponiblesFefo ne renvoie que les lots non perimes avec quantite > 0
        for (Lot lot : lotDao.trouverDisponiblesFefo(produitId, aujourdhui())) {
            // trouverDisponiblesFefo retourne les lots du produit dont la quantité est > 0
            // et qui ne sont pas périmés (RG-60), triés par date de péremption croissante.

            total += lot.getQuantiteDisponible();
        }
        return total;
    }

    @Override
    public boolean verifierDisponibilite(int produitId, int qte) {
        return qte > 0 && qte <= calculerStock(produitId); // RG-31 : on ne peut pas depasser le stock
    }

    @Override
    public List<VenteLot> prelever(int produitId, int qte) {
        List<VenteLot> preleves = new ArrayList<>();

        // on verifie AVANT de toucher aux lots : si impossible, rien n'est modifie
        if (!verifierDisponibilite(produitId, qte)) {
            return preleves; // liste vide = rien preleve
        }

        int reste = qte;
        // lots deja tries par peremption croissante (FEFO, RG-32)
        for (Lot lot : lotDao.trouverDisponiblesFefo(produitId, aujourdhui())) {
            if (reste == 0) {
                break;
            }
            int aPrendre = Math.min(reste, lot.getQuantiteDisponible()); // math.min retroune le min emntre les deux val
                                                                         // passes en arg

            // retirerQuantite renvoie false si le lot n'a plus assez de stock : ne jamais
            // l'ignorer
            if (!lotDao.retirerQuantite(lot.getId(), aPrendre)) { // soit true si aprendre egale a lot.getquantitedispo
                                                                  // qui soit egale a 0
                remettreEnStock(preleves);
                return new ArrayList<>();
            }
            preleves.add(new VenteLot(lot, aPrendre));
            reste -= aPrendre;
        }

        if (reste > 0) { // securite : le stock a change entre la verification et le prelevement
            remettreEnStock(preleves);
            return new ArrayList<>();
        }
        return preleves;
    }

    @Override
    public void restituer(Vente v) {
        if (v == null) {
            return;
        }
        for (LigneVente ligne : v.getLignes()) {
            for (VenteLot vl : ligne.getVenteLots()) {
                lotDao.ajouterQuantite(vl.getLot().getId(), vl.getQuantitePrelevee());
            }
        }
    }

    @Override
    public List<Lot> lotsDuProduit(int produitId) {
        return lotDao.trouverParProduit(produitId);
    }

    // annule les retraits deja faits dans prelever (pour ne jamais laisser un
    // prelevement a moitie)
    private void remettreEnStock(List<VenteLot> deja) {
        for (VenteLot vl : deja) {
            lotDao.ajouterQuantite(vl.getLot().getId(), vl.getQuantitePrelevee());
        }
    }
}
