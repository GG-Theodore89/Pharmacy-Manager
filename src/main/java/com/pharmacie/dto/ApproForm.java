package com.pharmacie.dto;
import java.time.LocalDate;
import java.util.List;

public class ApproForm {
    private Integer fournisseurId ;
    private LocalDate dateReception ;
    private List<LigneApproForm> lignes ;
    public ApproForm(Integer fournisseurId,LocalDate dateReception,List<LigneApproForm> lignes){
        this.fournisseurId = fournisseurId;
        this.dateReception = dateReception ;
        this.lignes = lignes ;
    }
   public Integer getFournisseurId(){
        return fournisseurId;
   }
   public LocalDate getDateReception(){
        return dateReception;
   }

    public List<LigneApproForm> getLignes() {
        return lignes;
    }
}
