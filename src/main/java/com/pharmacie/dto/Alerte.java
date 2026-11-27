
package com.pharmacie.dto;

import com.pharmacie.model.Lot;
import com.pharmacie.model.Produit;
import com.pharmacie.service.TypeAlerte;

public class Alerte {

    //the attributes are declared finale so as to not be changed when an error is produced

    private final Produit produit;
    private final TypeAlerte type;
    private final Lot lot;
    private final String message;
    private final int quantite;

    //the constructor is necessary for the usage of final in the declaration of the attributs

    public Alerte(Produit p, TypeAlerte ta, Lot l, String m, int q) {
        // Vérifier la validité des paramètres obligatoires.
        if (p == null || ta == null || q < 0) {
            throw new IllegalArgumentException(
                "wrong parameters for the constructor"
            );
        }

        // Une alerte de péremption nécessite un lot.
        if ((ta == TypeAlerte.PERIME || ta == TypeAlerte.PEREMPTION_PROCHE) && l == null) {
            throw new IllegalArgumentException(
                "with perime or peremption proche you need a lot"
            );
        }

        // Les alertes de stock concernent un produit, pas un lot.
        if ((ta == TypeAlerte.RUPTURE || ta == TypeAlerte.STOCK_BAS) && l != null) {
            throw new IllegalArgumentException(
                "the constructor doesnt need a lot in case of rupture or bas"
            );
        }

        // Une rupture correspond à un stock vendable nul.
        if (ta == TypeAlerte.RUPTURE && q != 0) {
            throw new IllegalArgumentException(
                "not yet a rupture quantity is not 0"
            );
        }

        // Les autres alertes nécessitent une quantité positive.
        if (ta != TypeAlerte.RUPTURE && q == 0) {
            throw new IllegalArgumentException(
                "no product to analyse"
            );
        }

        // Vérifier que le message est renseigné et non vide.
        if (m == null || m.isBlank()) {
            throw new IllegalArgumentException(
                "not a useful msg"
            );
        }

        this.produit = p;
        this.type = ta;
        this.lot = l;
        this.message = m;
        this.quantite = q;
    }

    //the second constructor is for we ned to create an a lert related to a pruduct independently of the lot it came from (like RUPTURE)

    public Alerte(Produit p, TypeAlerte ta, String m, int q) {
        this(p, ta, null, m, q);
    }

    public int getQuantite() {
        return this.quantite;
    }

    public TypeAlerte getTypeAlert() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }

    public Produit getProduit() {
        return this.produit;
    }

    public Lot getLot() {
        return this.lot;
    }
}
