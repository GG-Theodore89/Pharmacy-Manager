package com.pharmacie.service;

import java.util.List;

import com.pharmacie.dto.Alerte;

public interface AlerteService {

    List<Alerte> consulter(TypeAlerte type);

    List<Alerte> toutesLesAlertes();

}
