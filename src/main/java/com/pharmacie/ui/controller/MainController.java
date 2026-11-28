package com.pharmacie.ui.controller;

import com.pharmacie.model.Utilisateur;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.AuthService;
import com.pharmacie.ui.Page;
import com.pharmacie.ui.PageNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class MainController {

    private final AuthService authService;
    private final SessionManager sessionManager;
    private final Runnable onLogout;
    private final PageNavigator navigator;

    @FXML private Label welcomeLabel;
    @FXML private Label subtitleLabel;
    @FXML private TabPane workspaceTabs;

    public MainController(AuthService authService,
                          SessionManager sessionManager,
                          Runnable onLogout,
                          PageNavigator navigator) {
        this.authService = authService;
        this.sessionManager = sessionManager;
        this.onLogout = onLogout;
        this.navigator = navigator;
    }

    @FXML
    private void initialize() {
        navigator.bindWorkspace(workspaceTabs);

        Utilisateur utilisateur = sessionManager.getUtilisateurConnecte();
        String prenom = utilisateur == null ? null : utilisateur.getPrenom();

        if (prenom != null && !prenom.isBlank()) {
            welcomeLabel.setText("Bonjour " + prenom + " — Espace pharmacie");
        }
    }

    @FXML
    private void handleOpenPage(ActionEvent event) {
        if (event.getSource() instanceof MenuItem item
            && item.getUserData() instanceof String code) {
            try {
                navigator.open(Page.valueOf(code));
            } catch (IllegalArgumentException exception) {
                UiFeedback.erreur(exception);
            }
        }
    }

    @FXML
    private void handleDeconnexion() {
        authService.deconnecter();
        onLogout.run();
    }
}
