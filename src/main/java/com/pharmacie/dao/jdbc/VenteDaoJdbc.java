package com.pharmacie.dao.jdbc;

import com.pharmacie.dao.VenteDao;
import com.pharmacie.model.Vente;
import java.time.LocalDate;
import java.util.List;

public class VenteDaoJdbc implements VenteDao {

    @Override
    public List<Vente> listerParPeriode(LocalDate debut, LocalDate fin) {
        return List.of();
    }

    @Override
    public Vente trouverParId(int id) {
        return null;
    }
}
