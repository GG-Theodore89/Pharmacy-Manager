package com.pharmacie.dao;
import com.pharmacie.model.Approvisionnement;
import java.util.List;

public interface ApprovisionnementDao {
    Approvisionnement enregistrer(Approvisionnement approvisionnement);
    List<Approvisionnement> listerTous();
}