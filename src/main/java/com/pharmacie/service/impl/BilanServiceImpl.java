package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.dao.VenteDao;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import com.pharmacie.model.Vente;
import com.pharmacie.model.StatutVente;
import com.pharmacie.dto.Bilan;

public class BilanServiceImpl implements BilanService {
    private final VenteDao venteDao;

    public BilanServiceImpl(VenteDao venteDao){
        this.venteDao = venteDao;
    }

    @Override
    public List<Vente> historique(LocalDate debut, LocalDate fin){
        if (debut == null || fin == null) {
            throw new DonneeInvalideException(
                "Les dates de début et de fin sont obligatoires."
            );
        }

        if (debut.isAfter(fin)) {
            throw new DonneeInvalideException(
                "La date de début doit être antérieure ou égale à la date de fin."
            );
        }

        return venteDao.listerParPeriode(debut, fin);
    }

    @Override
    public Vente detailVente(int venteId){
        if (venteId <= 0) {
            throw new DonneeInvalideException(
                "L'identifiant de la vente est invalide."
            );
        }

        Vente vente = venteDao.trouverParId(venteId);

        if (vente == null) {
            throw new EntiteIntrouvableException(
                "La vente demandée est introuvable."
            );
        }

        return vente;
    }

    @Override
    public Bilan genererBilan(LocalDate debut, LocalDate fin) {

        List<Vente> ventes = venteDao.listerParPeriode(debut, fin);

        int nombreVentes = 0;
        int nombreAnnulations = 0;
        BigDecimal chiffreAffaires = BigDecimal.ZERO;

        for (Vente vente : ventes) {

            if (vente.getStatut() == StatutVente.VALIDEE) {
                nombreVentes++;
                chiffreAffaires = chiffreAffaires.add(
                    vente.getMontantTotal()
                );
            }
            else if (vente.getStatut() == StatutVente.ANNULEE) {
                nombreAnnulations++;
            }
        }

        return new Bilan(
            debut,
            fin,
            chiffreAffaires,
            nombreVentes,
            nombreAnnulations,
            nombreVentes == 0
                ? BigDecimal.ZERO
                : chiffreAffaires.divide(
                    BigDecimal.valueOf(nombreVentes),
                    2,
                    RoundingMode.HALF_UP
                )
        );
    }
}
