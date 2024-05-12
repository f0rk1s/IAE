package com.example.iae;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileChooserApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Do nothing in the start method
    }

    // Method to show the file chooser dialog and return the selected file path
    public static String showFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            String sourceFilePath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + sourceFilePath);
            return sourceFilePath;
        } else {
            System.out.println("No file selected.");
            return "";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
