package com.pharmacie.dao.memoire;

import com.pharmacie.dao.UtilisateurDao;
import com.pharmacie.model.Utilisateur;

import java.util.HashMap;
import java.util.Map;

public class UtilisateurDaoMemoire implements UtilisateurDao {

    private final Map<String, Utilisateur> utilisateurs = new HashMap<>();

    @Override
    public void enregistrer(Utilisateur utilisateur) {
        utilisateurs.put(utilisateur.getEmail(), utilisateur);
    }

    @Override
    public Utilisateur trouverParEmail(String email) {
        return utilisateurs.get(email);
    }
}