package com.example.iae;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileChooserApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {

            String sourceFilePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + sourceFilePath);
        } else {
            System.out.println("No file selected.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
