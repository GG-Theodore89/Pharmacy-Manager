package com.pharmacie.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.pharmacie.config.AppConfig; 
import com.pharmacie.dto.Alerte;
import com.pharmacie.service.AlerteService;
import com.pharmacie.service.TypeAlerte;

public class AlerteServiceImpl implements AlerteService {

    @Override 

    public List<Alerte> consulter(TypeAlerte type){
        return List.of();
    }
    
    @Override 

    public List<Alerte> toutesLesAlertes(){
        return List.of();
    }

    private boolean estPerime(int quantiteDisponible, LocalDate datePeremption,LocalDate aujourdhui){
        return (quantiteDisponible >0 && datePeremption.isBefore(aujourdhui)) ;
}
private boolean estPeremptionProche(int quantiteDisponible,LocalDate datePeremption,LocalDate aujourdhui) {
    LocalDate dateLimite = aujourdhui.plusDays(AppConfig.getjoursAlertePeremption());
    return (!datePeremption.isBefore(aujourdhui) && !datePeremption.isAfter(dateLimite) && quantiteDisponible >0);
    //this condition checks if the datepremp is not before today meaning mazal mat perima then check if datepremp is not after the date limit hich means la date dyal premp 9riba
}
private boolean estRupture(boolean actif, int stockVendable) {

    return actif && stockVendable==0;
}
private boolean estStockBas(boolean actif, int stockVendable,int seuilAlerte) {

    return actif && stockVendable>=0 && stockVendable<=seuilAlerte;

}
    


}
