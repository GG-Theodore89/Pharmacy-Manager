package com.pharmacie;

import com.pharmacie.config.Services;
import com.pharmacie.dao.UtilisateurDao;
import com.pharmacie.dao.VenteDao;
import com.pharmacie.dao.memoire.UtilisateurDaoMemoire;
import com.pharmacie.dao.memoire.VenteDaoMemoire;
import com.pharmacie.security.SessionManager;
import com.pharmacie.service.AuthService;
import com.pharmacie.service.impl.AuthServiceImpl;
import com.pharmacie.service.impl.BilanServiceImpl;
import com.pharmacie.ui.AppState;
import com.pharmacie.ui.Page;
import com.pharmacie.ui.PageNavigator;
import com.pharmacie.ui.controller.AlertesController;
import com.pharmacie.ui.controller.ApprovisionnementsController;
import com.pharmacie.ui.controller.AuthController;
import com.pharmacie.ui.controller.BilansController;
import com.pharmacie.ui.controller.ClientsController;
import com.pharmacie.ui.controller.CompteController;
import com.pharmacie.ui.controller.MainController;
import com.pharmacie.ui.controller.PanierController;
import com.pharmacie.ui.controller.ProduitsController;
import com.pharmacie.ui.controller.StockController;
import com.pharmacie.ui.controller.TicketsController;
import com.pharmacie.ui.controller.VentesController;

import java.io.IOException;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class App extends Application implements PageNavigator {

    private Stage stage;
    private Scene scene;
    private final Services services = new Services();
    private SessionManager sessionManager;
    private AppState appState;
    private TabPane workspace;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        UtilisateurDao utilisateurDao = new UtilisateurDaoMemoire();
        VenteDao venteDao = new VenteDaoMemoire();
        sessionManager = new SessionManager();
        AuthService authService = new AuthServiceImpl(utilisateurDao, sessionManager);
        services.setAuthService(authService);
        services.setBilanService(new BilanServiceImpl(venteDao));
        appState = new AppState();

        scene = new Scene(new StackPane());
        scene.getStylesheets().add(
            getClass().getResource("/css/application.css").toExternalForm()
        );

        showAuthView();

        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    @Override
    public void bindWorkspace(TabPane workspace) {
        this.workspace = workspace;
    }

    @Override
    public void open(Page page) {
        if (workspace == null) {
            return;
        }

        Optional<Tab> existante = workspace.getTabs().stream()
            .filter(tab -> page.name().equals(tab.getUserData()))
            .findFirst();

        if (existante.isPresent()) {
            workspace.getSelectionModel().select(existante.get());
            return;
        }

        try {
            Parent contenu = loadView(page.getRessource(), type -> createController(type));
            Tab onglet = new Tab(page.getTitre(), contenu);
            onglet.setUserData(page.name());
            workspace.getTabs().add(onglet);
            workspace.getSelectionModel().select(onglet);
            stage.setTitle("Pharmacie — " + page.getTitre());
        } catch (IOException exception) {
            throw new RuntimeException("Impossible de charger " + page.getRessource(), exception);
        }
    }

    private void showAuthView() {
        try {
            Parent root = loadView(
                "/fxml/AuthView.fxml",
                type -> new AuthController(services.getAuthService(), this::showMainView)
            );
            scene.setRoot(root);
            stage.setTitle("Pharmacie - Authentification");
        } catch (IOException exception) {
            throw new RuntimeException("Impossible de charger AuthView.fxml", exception);
        }
    }

    private void showMainView() {
        workspace = null;
        try {
            Parent root = loadView(
                "/fxml/MainView.fxml",
                type -> new MainController(
                    services.getAuthService(),
                    sessionManager,
                    this::showAuthView,
                    this
                )
            );
            scene.setRoot(root);
            stage.setTitle("Pharmacie - Tableau de bord");
        } catch (IOException exception) {
            throw new RuntimeException("Impossible de charger MainView.fxml", exception);
        }
    }

    private Object createController(Class<?> type) {
        if (type == CompteController.class) {
            return new CompteController(
                services.getAuthService(), sessionManager, this::showAuthView
            );
        }
        if (type == ProduitsController.class) {
            return new ProduitsController(services.getProduitService());
        }
        if (type == StockController.class) {
            return new StockController(
                services.getStockService(), services.getProduitService(), appState
            );
        }
        if (type == ApprovisionnementsController.class) {
            return new ApprovisionnementsController(
                services.getApprovisionnementService(),
                services.getProduitService(),
                sessionManager
            );
        }
        if (type == PanierController.class) {
            return new PanierController(
                appState.getPanier(), services.getProduitService(), sessionManager
            );
        }
        if (type == VentesController.class) {
            return new VentesController(
                services.getVenteService(),
                services.getTicketService(),
                services.getClientService(),
                services.getBilanService(),
                appState,
                sessionManager
            );
        }
        if (type == TicketsController.class) {
            return new TicketsController(services.getTicketService(), appState);
        }
        if (type == ClientsController.class) {
            return new ClientsController(services.getClientService(), appState);
        }
        if (type == AlertesController.class) {
            return new AlertesController(services.getAlerteService());
        }
        if (type == BilansController.class) {
            return new BilansController(services.getBilanService(), appState);
        }
        throw new IllegalArgumentException("Contrôleur inconnu : " + type.getName());
    }

    private Parent loadView(String resource,
                            Callback<Class<?>, Object> controllerFactory) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        loader.setControllerFactory(controllerFactory);
        return loader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
