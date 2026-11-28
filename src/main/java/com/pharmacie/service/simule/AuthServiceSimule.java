package com.pharmacie.service.simule;

import com.pharmacie.dto.NouvelUtilisateur;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.security.PasswordUtil;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.AuthService;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceSimule implements AuthService {

    private final Map<String, Utilisateur> utilisateurs = new HashMap<>();
    private final SessionManager sessionManager = new SessionManager();

    @Override
    public Utilisateur inscrire(NouvelUtilisateur u) {
        Utilisateur utilisateur = new Utilisateur(
            0,
            u.getNom(),
            u.getPrenom(),
            u.getAdresse(),
            u.getEmail(),
            PasswordUtil.hash(u.getMotDePasse()),
            true
        );

        utilisateurs.put(utilisateur.getEmail(), utilisateur);
        return utilisateur;
    }

    @Override
    public Utilisateur authentifier(String email, String mdp) {
        Utilisateur utilisateur = utilisateurs.get(email);

        if (utilisateur == null ||
            !PasswordUtil.verify(mdp, utilisateur.getMotDePasseHash())) {
            return null;
        }

        sessionManager.ouvrirSession(utilisateur);
        return utilisateur;
    }

    @Override
    public void deconnecter() {
        sessionManager.fermerSession();
    }
}
