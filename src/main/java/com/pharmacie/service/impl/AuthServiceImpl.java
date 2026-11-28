package com.pharmacie.service.impl;

import com.pharmacie.service.*;
import com.pharmacie.dto.NouvelUtilisateur;
import com.pharmacie.exception.AuthentificationException;
import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.exception.RegleMetierException;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.dao.UtilisateurDao;
import com.pharmacie.security.PasswordUtil;
import com.pharmacie.security.SessionManager;

import java.util.Locale;

public class AuthServiceImpl implements AuthService {
    private static final String REGEX_EMAIL =
        "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

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

        String email = normaliserEmail(u.getEmail());

        if (email == null || email.isBlank()) {
            throw new DonneeInvalideException(
                "L'adresse e-mail est obligatoire."
            );
        }

        if (!emailValide(email)) {
            throw new DonneeInvalideException(
                "L'adresse e-mail n'est pas valide."
            );
        }

        if (u.getMotDePasse() == null || u.getMotDePasse().isBlank()) {
            throw new DonneeInvalideException(
                "Le mot de passe est obligatoire."
            );
        }

        if (utilisateurDAO.trouverParEmail(email) != null) {
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
            email,
            hash,
            true
        );

        utilisateurDAO.enregistrer(utilisateur);

        return utilisateur;
    }

    @Override
    public Utilisateur authentifier(String email, String mdp){
        String emailNormalise = normaliserEmail(email);

        if (!emailValide(emailNormalise)) {
            throw new AuthentificationException(
                "Email ou mot de passe incorrect"
            );
        }

        // 1. Find user by email
        Utilisateur utilisateur = utilisateurDAO.trouverParEmail(emailNormalise);

        // 2. Verify credentials
        if (utilisateur == null ||
            !utilisateur.isActif() ||
            mdp == null ||
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

    private String normaliserEmail(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }

    private boolean emailValide(String email) {
        return email != null && email.matches(REGEX_EMAIL);
    }

    @Override
    public void deconnecter(){
        // delete the current session if existed
        sessionManager.fermerSession();
    }
}

