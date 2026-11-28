package com.pharmacie.service.impl;

import com.pharmacie.dao.ApprovisionnementDao;
import com.pharmacie.dao.LotDao;
import com.pharmacie.dao.ProduitDao;
import com.pharmacie.dto.ApproForm;
import com.pharmacie.dto.LigneApproForm;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.model.Approvisionnement;
import com.pharmacie.service.ApprovisionnementService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApprovisionnementServiceImpl implements ApprovisionnementService {

    // Ces DAO seront utilisés dans les étapes suivantes.
    private final ApprovisionnementDao approvisionnementDao;
    private final LotDao lotDao;
    private final ProduitDao produitDao;

    public ApprovisionnementServiceImpl(
            ApprovisionnementDao approvisionnementDao,
            LotDao lotDao,
            ProduitDao produitDao) {
        this.approvisionnementDao = approvisionnementDao;
        this.lotDao = lotDao;
        this.produitDao = produitDao;
    }

    @Override
    public Approvisionnement enregistrer(ApproForm form, int userId) {
        validerFormulaire(form, userId);

        // La sauvegarde sera ajoutée après le raccordement aux DAO
        // et à la transaction.
        return null;
    }

    @Override
    public List<Approvisionnement> historique() {
        // Le chargement de l'historique sera ajouté avec les DAO.
        return List.of();
    }

    private void validerFormulaire(ApproForm form, int userId) {
        if (userId <= 0) {
            throw new DonneeInvalideException();
        }

        if (form == null) {
            throw new DonneeInvalideException();
        }

        if (form.getDateReception() == null) {
            throw new DonneeInvalideException();
        }

        Integer fournisseurId = form.getFournisseurId();
        if (fournisseurId != null && fournisseurId <= 0) {
            throw new DonneeInvalideException();
        }

        if (form.getLignes() == null || form.getLignes().isEmpty()) {
            throw new DonneeInvalideException();
        }

        Map<Integer, Set<String>> numerosParProduit = new HashMap<>();

        for (LigneApproForm ligne : form.getLignes()) {
            if (ligne == null) {
                throw new DonneeInvalideException();
            }

            if (ligne.getProduitId() <= 0) {
                throw new DonneeInvalideException();
            }

            if (ligne.getNumeroLot() == null
                    || ligne.getNumeroLot().trim().isEmpty()) {
                throw new DonneeInvalideException();
            }

            if (ligne.getQuantiteRecue() <= 0) {
                throw new DonneeInvalideException();
            }

            if (ligne.getDatePeremption() == null
                    || ligne.getDatePeremption().isBefore(form.getDateReception())) {
                throw new DonneeInvalideException();
            }

            String numeroLotNormalise = ligne.getNumeroLot().trim();

            Set<String> numerosDuProduit = numerosParProduit.computeIfAbsent(
                    ligne.getProduitId(),
                    id -> new HashSet<>()
            );

            if (!numerosDuProduit.add(numeroLotNormalise)) {
                throw new DonneeInvalideException();
            }
        }
    }
}