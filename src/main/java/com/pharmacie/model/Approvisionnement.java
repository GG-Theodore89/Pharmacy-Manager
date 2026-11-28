package com.pharmacie.model;
import java.time.LocalDate;
import java.util.List;
import com.pharmacie.model.Lot;


public class Approvisionnement {
    private int id ;
    private int  utilisateurId ;
    private LocalDate dateApprovisionnement ;
    private LocalDate dateReception ;
    private Integer fournisseurId ;
    private List<Lot> lots ;
   public Approvisionnement(int id , int utilisateurId, LocalDate dateApprovisionnement, LocalDate dateReception, Integer fournisseurId , List<Lot> lots ){
       this.id = id ;
       this.utilisateurId = utilisateurId ;
       this.dateApprovisionnement = dateApprovisionnement ;
       this.dateReception = dateReception ;
       this.fournisseurId = fournisseurId ;
       this.lots = lots ;
   }
   public int getId(){
       return id ;
   }
   public int getUtilisateurId(){
       return utilisateurId ;
   }
   public LocalDate getDateApprovisionnement(){
       return dateApprovisionnement ;
   }
   public LocalDate getDateReception(){
       return dateReception ;
   }
   public Integer getFournisseurId(){
       return fournisseurId;
   }
    public List<Lot> getLots() {
        return lots;
    }
}
