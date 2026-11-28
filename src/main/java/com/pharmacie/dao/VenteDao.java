package com.pharmacie.dao;

import com.pharmacie.model.Vente;
import java.time.LocalDate;
import java.util.List;

public interface VenteDao {

    List<Vente> listerParPeriode(LocalDate debut, LocalDate fin);

    Vente trouverParId(int id);
}
