package com.pharmacie.service;

import com.pharmacie.model.Vente;
import com.pharmacie.model.Panier;

import java.util.List;
import java.time.LocalDate;

public interface VenteService {

    // Valider et enregistrer une vente
    Vente validerVente(Panier panier, int utilisateur_id, Integer client_id);

    // Annuler une vente déjà enregistrée
    void annulerVente(int vente_id, int annule_par_id, String motif);

    // Consulter toutes les informations d'une vente
    Vente consulterVente(int vente_id);

    // Consulter l'historique ou rechercher des ventes
    List<Vente> rechercherVentes(Integer vente_id ,String Client_nom ,LocalDate dateDebut,LocalDate dateFin);

}
