
package com.pharmacie.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.pharmacie.config.AppConfig;
import com.pharmacie.dao.LotDao;
import com.pharmacie.dao.ProduitDao;
import com.pharmacie.dto.Alerte;
import com.pharmacie.model.Lot;
import com.pharmacie.model.Produit;
import com.pharmacie.service.AlerteService;
import com.pharmacie.service.StockService;
import com.pharmacie.service.TypeAlerte;

public class AlerteServiceImpl implements AlerteService {
    private final LotDao lotDao;
    private final ProduitDao produitDao;
    private final StockService stockService;

    public AlerteServiceImpl(LotDao ldao, ProduitDao pdao, StockService stk) {
        this.lotDao = ldao;
        this.produitDao = pdao;
        this.stockService = stk;
    }

    @Override
    public List<Alerte> consulter(TypeAlerte type) {
        List<Alerte> alertes = new ArrayList<>();
        if (type == null) {
            throw new IllegalArgumentException(
                "type cant be null"
            );
        }
        switch (type) {
            case PERIME -> {
                List<Lot> lots = lotDao.listerAvecQuantitePositive();
                for( Lot lot : lots){
                    
                }
            }
            case PEREMPTION_PROCHE -> {}
            case RUPTURE -> {
                List<Produit> produits = produitDao.listerTous();
                for (Produit produit : produits) {
                    boolean actif = produit.isActif();
                    if(!actif) continue;
                }
            }
            case STOCK_BAS -> {
                List<Produit> produits = produitDao.listerTous();
                for (Produit produit : produits) {
                    boolean actif = produit.isActif();
                    if(!actif) continue;
                    int seuilAlerte = produit.getSeuilAlerte();
                }
            }
        }
        return alertes;
    }

    @Override
    public List<Alerte> toutesLesAlertes() {
        List<Alerte> alertes = new ArrayList<>();
        alertes.addAll(consulter(TypeAlerte.PERIME));
        alertes.addAll(consulter(TypeAlerte.PEREMPTION_PROCHE));
        alertes.addAll(consulter(TypeAlerte.RUPTURE));
        alertes.addAll(consulter(TypeAlerte.STOCK_BAS));
        return alertes;
    }

    private boolean estPerime(int quantiteDisponible, LocalDate datePeremption, LocalDate aujourdhui) {
        return (quantiteDisponible > 0 && datePeremption.isBefore(aujourdhui));
    }

    private boolean estPeremptionProche(int quantiteDisponible, LocalDate datePeremption, LocalDate aujourdhui) {
        LocalDate dateLimite = aujourdhui.plusDays(AppConfig.getjoursAlertePeremption());

        return (!datePeremption.isBefore(aujourdhui) && !datePeremption.isAfter(dateLimite) && quantiteDisponible > 0);
        //this condition checks if the datepremp is not before today meaning mazal mat perima then check if datepremp is not after the date limit hich means la date dyal premp 9riba
    }

    private boolean estRupture(boolean actif, int stockVendable) {
        return actif && stockVendable == 0;
    }

    private boolean estStockBas(boolean actif, int stockVendable, int seuilAlerte) {
        return actif && stockVendable > 0 && stockVendable <= seuilAlerte;
    }
}
