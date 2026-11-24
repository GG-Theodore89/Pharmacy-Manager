package com.pharmacie.service.simule;

import java.util.List;

import com.pharmacie.dto.Alerte;
import com.pharmacie.service.*;

public class AlerteServiceSimule implements AlerteService {
        @Override 

    public List<Alerte> consulter(TypeAlerte type){
        return List.of();
    }
    
    @Override 

    public List<Alerte> toutesLesAlertes(){
        return List.of();
    }
}
