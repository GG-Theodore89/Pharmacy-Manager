package com.pharmacie.dao;
import com.pharmacie.model.Lot;
import java.util.List;

public interface LotDao {
    Lot enregistrer(Lot lot);
    List<Lot> trouverParApprovisionnement(int approvisionnementId);
    boolean existeParProduitEtNumero(int produitId, String numeroLot);
}