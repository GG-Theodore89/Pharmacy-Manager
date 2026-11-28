package com.pharmacie.dao.memoire;

import com.pharmacie.dao.VenteDao;
import com.pharmacie.model.Vente;
import java.time.LocalDate;
import java.util.List;

public class VenteDaoMemoire implements VenteDao {

    @Override
    public List<Vente> listerParPeriode(LocalDate debut, LocalDate fin) {
        return List.of();
    }

    @Override
    public Vente trouverParId(int id) {
        return null;
    }
}
