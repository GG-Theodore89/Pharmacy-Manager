package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.dao.UtilisateurDao;
import com.pharmacie.security.PasswordUtil;
import com.pharmacie.security.SessionManager;

public class AuthServiceImpl implements AuthService {
    private final UtilisateurDao utilisateurDAO;
    private final SessionManager sessionManager;

    public AuthServiceImpl(
            UtilisateurDao utilisateurDAO,
            SessionManager sessionManager) {

        this.utilisateurDAO = utilisateurDAO;
        this.sessionManager = sessionManager;
    }


    @Override
    public Utilisateur inscrire(NouvelUtilisateur u) {

        if (u == null) {
            throw new DonneeInvalideException(
                "Les informations d'inscription sont obligatoires."
            );
        }

        if (u.getEmail() == null || u.getEmail().isBlank()) {
            throw new DonneeInvalideException(
                "L'adresse e-mail est obligatoire."
            );
        }

        if (u.getMotDePasse() == null || u.getMotDePasse().isBlank()) {
            throw new DonneeInvalideException(
                "Le mot de passe est obligatoire."
            );
        }

        if (utilisateurDAO.trouverParEmail(u.getEmail()) != null) {
            throw new RegleMetierException(
                "Cette adresse e-mail est déjà utilisée."
            );
        }


        String hash = PasswordUtil.hash(u.getMotDePasse());

        Utilisateur utilisateur = new Utilisateur(
            0,
            u.getNom(),
            u.getPrenom(),
            u.getAdresse(),
            u.getEmail(),
            hash,
            true
        );

        utilisateurDAO.enregistrer(utilisateur);

        return utilisateur;
    }

    @Override
    public Utilisateur authentifier(String email, String mdp){
        // 1. Find user by email
        Utilisateur utilisateur = utilisateurDAO.trouverParEmail(email);

        // 2. Verify credentials
        if (utilisateur == null ||
            !utilisateur.isActif() ||
            !PasswordUtil.verify(mdp, utilisateur.getMotDePasseHash())) {

            throw new AuthentificationException(
                "Email ou mot de passe incorrect"
            );
        }

        // 3. Create session
        sessionManager.ouvrirSession(utilisateur);

        // 4. Return authenticated user
        return utilisateur;
    }

    @Override
    public void deconnecter(){
        // delete the current session if existed
        sessionManager.fermerSession();
    }
}

