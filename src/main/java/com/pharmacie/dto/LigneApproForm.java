package com.pharmacie.dto;
import java.time.LocalDate;

public class LigneApproForm {
    private int produitId ;
    private String numeroLot ;
    private  LocalDate datePeremption ;
    private int quantiteRecue ;
    public LigneApproForm(int produitId,String numeroLot, LocalDate datePeremption, int quantiteRecue ) {
        this.produitId = produitId;
        this.numeroLot = numeroLot;
        this.datePeremption = datePeremption;
        this.quantiteRecue = quantiteRecue;
    }
    public int getProduitId(){
        return produitId ;
    }
    public String getNumeroLot(){
        return numeroLot;
    }
    public LocalDate getDatePeremption(){
        return datePeremption;
    }
    public int getQuantiteRecue(){
        return quantiteRecue;
    }
}
