package com.pharmacie.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
@Data 
public class Client extends Personne {
    private String telephone;
    private String email;
    private boolean actif;
    private String Ordonence;
    private List<Vente> Ventes = new ArrayList<>() ;

    public Client(Integer id,String nom,String prenom,String adresse,String telephone,String email,boolean actif,String Ordonence){
        super(id, nom, prenom, adresse);
        this.telephone=telephone;
        this.email=email;
        this.actif=actif;
        this.Ordonence=Ordonence;
    }
        public Client(String nom,String prenom,String adresse,String telephone,String email,boolean actif,String Ordonence){
        super(nom, prenom, adresse);
        this.telephone=telephone;
        this.email=email;
        this.actif=actif;
        this.Ordonence=Ordonence;
    }
}
