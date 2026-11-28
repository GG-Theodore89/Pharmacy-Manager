package com.pharmacie.ui.controller;

import com.pharmacie.dto.NouvelUtilisateur;
import com.pharmacie.model.Utilisateur;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CompteController {

    private final AuthService authService;
    private final SessionManager sessionManager;
    private final Runnable onLogout;

    @FXML private Label sessionLabel;
    @FXML private Label messageLabel;
    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField adresseField;
    @FXML private TextField emailField;
    @FXML private PasswordField motDePasseField;
    @FXML private Label motDePasseSuggestionLabel;

    public CompteController(AuthService authService,
                            SessionManager sessionManager,
                            Runnable onLogout) {
        this.authService = authService;
        this.sessionManager = sessionManager;
        this.onLogout = onLogout;
    }

    @FXML
    private void initialize() {
        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        sessionLabel.setText(utilisateur == null
            ? "Aucun utilisateur connecté."
            : utilisateur.getPrenom() + " " + utilisateur.getNom()
              + " — " + utilisateur.getEmail());

        motDePasseField.textProperty().addListener(
            (observable, ancien, valeur) -> UiFeedback.suggestionMotDePasse(
                motDePasseSuggestionLabel, valeur
            )
        );
    }

    @FXML
    private void handleInscrire() {
        messageLabel.setText("");
        NouvelUtilisateur nouvelUtilisateur = new NouvelUtilisateur(
            UiFeedback.texte(nomField),
            UiFeedback.texte(prenomField),
            UiFeedback.texte(adresseField),
            UiFeedback.texte(emailField),
            motDePasseField.getText()
        );

        UiFeedback.executer(
            () -> authService.inscrire(nouvelUtilisateur),
            () -> {
                messageLabel.setText("Compte créé pour " + nouvelUtilisateur.getEmail() + ".");
                effacerChamps();
            }
        );
    }

    @FXML
    private void handleDeconnexion() {
        authService.deconnecter();
        onLogout.run();
    }

    @FXML
    private void handleEffacer() {
        effacerChamps();
        messageLabel.setText("");
    }

    private void effacerChamps() {
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        emailField.clear();
        motDePasseField.clear();
        UiFeedback.suggestionMotDePasse(motDePasseSuggestionLabel, "");
    }
}
