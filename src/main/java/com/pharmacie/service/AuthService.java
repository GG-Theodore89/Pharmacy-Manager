package com.pharmacie.service;

import com.pharmacie.dto.NouvelUtilisateur;
import com.pharmacie.model.Utilisateur;

public interface AuthService {
    int LONGUEUR_MOT_DE_PASSE_CONSEILLEE = 8;

    public Utilisateur inscrire(NouvelUtilisateur u);
    public Utilisateur authentifier(String email, String mdp);
    public void deconnecter();
}
