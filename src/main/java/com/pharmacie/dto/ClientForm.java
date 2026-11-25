package com.pharmacie.dto;
import lombok.Data;

@Data 
public class ClientForm {
    private Integer id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private boolean actif;
    private String Ordonence;
}
