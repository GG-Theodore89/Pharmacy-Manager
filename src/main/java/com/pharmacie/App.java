
package com.pharmacie;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        Label message = new Label("Pharmacie Management System");

        StackPane root = new StackPane(message);

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("Pharmacie");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
