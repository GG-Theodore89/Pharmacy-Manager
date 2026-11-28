package com.pharmacie;

import com.pharmacie.dao.UtilisateurDao;
import com.pharmacie.dao.memoire.UtilisateurDaoMemoire;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.AuthService;
import com.pharmacie.service.impl.AuthServiceImpl;
import com.pharmacie.ui.controller.AuthController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // 1. Initialize dependencies
        UtilisateurDao utilisateurDao = new UtilisateurDaoMemoire();
        SessionManager sessionManager = new SessionManager();

        AuthService authService = new AuthServiceImpl(
            utilisateurDao,
            sessionManager
        );

        // 2. Load authentication interface
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/fxml/AuthView.fxml")
        );

        // 3. Inject AuthService into the controller
        loader.setControllerFactory(type -> {
            if (type == AuthController.class) {
                return new AuthController(authService);
            }
            throw new IllegalArgumentException("Contrôleur inconnu : " + type);
        });

        Parent root = loader.load();

        // 4. Display window
        stage.setTitle("Pharmacie - Authentification");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}