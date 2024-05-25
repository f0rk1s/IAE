package com.example.iae;

import java.io.IOException;
import java.net.URL; // Correct import statement

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IntegratedAssignmentEnvApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Corrected the URL import issue
        URL fxmlLocation = getClass().getResource("/com/example/iae/main-page.fxml");
        System.out.println("FXML Location: " + fxmlLocation);

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 600, 618);
        stage.setTitle("Integrated Assignment Environment");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
