package com.pharmacie.dao;

import com.pharmacie.model.Vente;
import java.util.List;
import java.time.LocalDate;

public interface VenteDAO {

    // Enregistrer une nouvelle vente
    int ajouter(Vente vente);

    // Rechercher une vente par son identifiant
    Vente trouverParId(int vente_id);

    // Modifier les informations d'une vente
    void modifier(Vente vente);

    // Rechercher des ventes selon différents critères
    List<Vente> rechercher(Integer vente_id, String client_nom,
                           LocalDate dateDebut, LocalDate dateFin);
}