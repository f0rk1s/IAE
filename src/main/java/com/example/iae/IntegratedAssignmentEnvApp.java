package com.example.iae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class IntegratedAssignmentEnvApp extends Application {
    @Override
    public void start(Stage stage) {
        try {
            URL fxmlLocation = getClass().getResource("/com/example/iae/main-page.fxml");
            if (fxmlLocation == null) {
                throw new IOException("FXML file not found!");
            }

            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 600, 618);

            URL cssLocation = getClass().getResource("/com/example/iae/style.css");
            if (cssLocation != null) {
                scene.getStylesheets().add(cssLocation.toExternalForm());
            } else {
                System.out.println("CSS file not found!");
            }

            stage.setTitle("Integrated Assignment Environment");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load the main page.");
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
