package com.example.iae;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IntegratedAssignmentEnvApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IntegratedAssignmentEnvApp.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 618);
        stage.setTitle("Integrated Assignment Environment");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}