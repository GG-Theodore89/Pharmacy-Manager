package com.pharmacie.service.impl;

import com.pharmacie.service.*;

public class BilanServiceImpl implements BilanService {
    public List<Vente> historique(LocalDate debut, LocalDate fin){
        // db method that search for ventes that are between two dates
        // from table row to Vente object
    }
    public Vente detailVente(int venteId){
        // collect all details of a vente with a join by a venteId
    }
    public Bilan genererBilan(LocalDate debut, LocalDate fin){
        // ...
    }
}
