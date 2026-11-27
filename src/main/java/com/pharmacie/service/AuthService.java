package com.pharmacie.service;

public interface AuthService {
    public Utilisateur inscrire(NouvelUtilisateur u);
    public Utilisateur authentifier(String email, String mdp);
    public void deconnecter();
}
