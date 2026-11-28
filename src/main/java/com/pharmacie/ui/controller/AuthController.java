package com.pharmacie.ui.controller;

import com.pharmacie.dto.NouvelUtilisateur;
import com.pharmacie.exception.PharmacieException;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class AuthController {

    private final AuthService authService;
    private final Runnable onLoginSuccess;

    @FXML private TabPane authTabs;
    @FXML private Tab connexionTab;
    @FXML private Tab inscriptionTab;

    @FXML private TextField connexionEmailField;
    @FXML private PasswordField connexionMotDePasseField;
    @FXML private Label connexionErreurLabel;

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField inscriptionEmailField;
    @FXML private PasswordField inscriptionMotDePasseField;
    @FXML private Label inscriptionMotDePasseSuggestionLabel;
    @FXML private Label inscriptionErreurLabel;

    // The FXMLLoader must use a controller factory to supply AuthService.
    public AuthController(AuthService authService, Runnable onLoginSuccess) {
        this.authService = authService;
        this.onLoginSuccess = onLoginSuccess;
    }

    @FXML
    private void initialize() {
        inscriptionMotDePasseField.textProperty().addListener(
            (observable, ancien, valeur) -> UiFeedback.suggestionMotDePasse(
                inscriptionMotDePasseSuggestionLabel, valeur
            )
        );
    }

    @FXML
    private void handleConnexion() {
        connexionErreurLabel.setText("");

        String email = connexionEmailField.getText();
        String motDePasse = connexionMotDePasseField.getText();

        try {
            Utilisateur utilisateur = authService.authentifier(email, motDePasse);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Connexion réussie");
            alert.setHeaderText(null);
            alert.setContentText("Bienvenue " + utilisateur.getPrenom() + " !");
            alert.showAndWait();
            onLoginSuccess.run();

        } catch (PharmacieException e) {
            connexionErreurLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleInscription() {
        inscriptionErreurLabel.setText("");

        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String email = inscriptionEmailField.getText();
        String motDePasse = inscriptionMotDePasseField.getText();

        try {
            NouvelUtilisateur nouvelUtilisateur =
                new NouvelUtilisateur(nom, prenom, adresse, email, motDePasse);

            authService.inscrire(nouvelUtilisateur);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscription réussie");
            alert.setHeaderText(null);
            alert.setContentText("Votre compte a été créé. Vous pouvez maintenant vous connecter.");
            alert.showAndWait();

            clearInscriptionFields();
            authTabs.getSelectionModel().select(connexionTab);
            connexionEmailField.setText(email);
            connexionMotDePasseField.clear();

        } catch (PharmacieException e) {
            inscriptionErreurLabel.setText(e.getMessage());
        }
    }

    private void clearInscriptionFields() {
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        inscriptionEmailField.clear();
        inscriptionMotDePasseField.clear();
        UiFeedback.suggestionMotDePasse(inscriptionMotDePasseSuggestionLabel, "");
    }
}
