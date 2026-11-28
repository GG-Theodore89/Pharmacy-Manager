package com.pharmacie.service;

import com.pharmacie.dto.Alerte;
import java.util.List;

public interface AlerteService {

    List<Alerte> consulter(TypeAlerte type);

    List<Alerte> toutesLesAlertes();
}
