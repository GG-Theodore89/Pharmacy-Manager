package com.pharmacie.service.impl;

import com.pharmacie.service.*;

public class AuthServiceImpl implements AuthService {
    
    public Utilisateur inscrire(NouvelUtilisateur u){
        // methods i need here:
        // 1. hash method to hash the mdp
        // 2. add new user method
    }

    public Utilisateur authentifier(String email, String mdp){
        // methods i need here:
        // 1. hash method to hash the mdp
        // 2. verify the email and hashed mdp method from the db
        // 2.1 create a session instance if the credientials are valid
        // 2.2 Otherwise raise an exception of credientials not valid
    }

    public void deconnecter(){
        // delete the current session if existed
    }
}

