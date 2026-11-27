package com.pharmacie.security;

import com.pharmacie.model.Utilisateur;

public class SessionManager {

    private Utilisateur utilisateurConnecte;

    public void ouvrirSession(Utilisateur utilisateur) {
        this.utilisateurConnecte = utilisateur;
    }

    public void fermerSession() {
        this.utilisateurConnecte = null;
    }

    public Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public boolean estConnecte() {
        return utilisateurConnecte != null;
    }
}