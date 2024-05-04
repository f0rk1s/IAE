package com.example.iae;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FolderChooser extends Application {
    @Override
    public void start(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        File selectedFolder = directoryChooser.showDialog(primaryStage);

        if (selectedFolder != null) {
            String sourceFolder = selectedFolder.getAbsolutePath();
            System.out.println("Selected folder: " + sourceFolder);

            // Now you can use sourceFolder as the selected folder
            // and proceed with your extraction process.
        } else {
            System.out.println("No folder selected.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
