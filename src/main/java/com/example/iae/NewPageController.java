package com.example.iae;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPageController {

    @FXML
    private void goToMainPage(ActionEvent event) {
        switchScene("/com/example/iae/main-page.fxml", event);
    }

    @FXML
    private void goBack(ActionEvent event) {
        switchScene("/com/example/iae/main-page.fxml", event);
    }

    private void switchScene(String fxmlFilePath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Failed to load the page: " + fxmlFilePath);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
