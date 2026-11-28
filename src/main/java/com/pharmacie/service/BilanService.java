package com.pharmacie.service;

import com.pharmacie.dto.Bilan;
import com.pharmacie.model.Vente;
import java.time.LocalDate;
import java.util.List;

public interface BilanService {
    public List<Vente> historique(LocalDate debut, LocalDate fin);
    public Vente detailVente(int venteId);
    public Bilan genererBilan(LocalDate debut, LocalDate fin);
}
