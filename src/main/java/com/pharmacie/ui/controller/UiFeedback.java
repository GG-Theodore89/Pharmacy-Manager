package com.pharmacie.ui.controller;

import com.pharmacie.exception.DonneeInvalideException;
import com.pharmacie.service.AuthService;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class UiFeedback {

    private UiFeedback() {
    }

    public static void information(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pharmacie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void erreur(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(
            exception.getMessage() == null
                ? "Une erreur inattendue s'est produite."
                : exception.getMessage()
        );
        alert.showAndWait();
    }

    public static void executer(Runnable action, Runnable apresSucces) {
        try {
            action.run();
            apresSucces.run();
        } catch (RuntimeException exception) {
            erreur(exception);
        }
    }

    public static int entier(TextField champ, String libelle) {
        String texte = texte(champ);
        if (texte.isEmpty()) {
            throw new DonneeInvalideException(libelle + " est obligatoire.");
        }
        try {
            return Integer.parseInt(texte);
        } catch (NumberFormatException exception) {
            throw new DonneeInvalideException(libelle + " doit être un nombre entier.");
        }
    }

    public static Integer entierOptionnel(TextField champ) {
        String texte = texte(champ);
        if (texte.isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(texte);
        } catch (NumberFormatException exception) {
            throw new DonneeInvalideException("L'identifiant doit être un nombre entier.");
        }
    }

    public static BigDecimal montant(TextField champ, String libelle) {
        String texte = texte(champ).replace(',', '.');
        if (texte.isEmpty()) {
            throw new DonneeInvalideException(libelle + " est obligatoire.");
        }
        try {
            return new BigDecimal(texte);
        } catch (NumberFormatException exception) {
            throw new DonneeInvalideException(libelle + " doit être un montant valide.");
        }
    }

    public static LocalDate date(DatePicker selecteur, String libelle) {
        LocalDate date = selecteur.getValue();
        if (date == null) {
            throw new DonneeInvalideException(libelle + " est obligatoire.");
        }
        return date;
    }

    public static String texte(TextField champ) {
        return champ.getText() == null ? "" : champ.getText().trim();
    }

    public static void suggestionMotDePasse(Label label, String motDePasse) {
        if (motDePasse != null && !motDePasse.isEmpty()
            && motDePasse.length() < AuthService.LONGUEUR_MOT_DE_PASSE_CONSEILLEE) {
            label.setText("Conseil : utilisez au moins "
                + AuthService.LONGUEUR_MOT_DE_PASSE_CONSEILLEE + " caractères.");
            label.setVisible(true);
            label.setManaged(true);
        } else {
            label.setText("");
            label.setVisible(false);
            label.setManaged(false);
        }
    }
}
