package com.pharmacie.service;

public interface BilanService {
    public List<Vente> historique(LocalDate debut, LocalDate fin);
    public Vente detailVente(int venteId);
    public Bilan genererBilan(LocalDate debut, LocalDate fin);
}
