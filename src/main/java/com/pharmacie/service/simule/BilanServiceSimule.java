package com.pharmacie.service.simule;

import com.pharmacie.dto.Bilan;
import com.pharmacie.model.Vente;
import com.pharmacie.service.BilanService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BilanServiceSimule implements BilanService {

    @Override
    public List<Vente> historique(LocalDate debut, LocalDate fin) {
        return List.of();
    }

    @Override
    public Vente detailVente(int venteId) {
        return null;
    }

    @Override
    public Bilan genererBilan(LocalDate debut, LocalDate fin) {
        return new Bilan(
            debut,
            fin,
            BigDecimal.ZERO,
            0,
            0,
            BigDecimal.ZERO
        );
    }
}
