package com.example.iae;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FolderChooserApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Do nothing in the start method
    }

    // Method to show the folder chooser dialog and return the selected folder path
    public static String showFolderChooser(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        File selectedFolder = directoryChooser.showDialog(stage);

        if (selectedFolder != null) {
            String sourceFolder = selectedFolder.getAbsolutePath();
            System.out.println("Selected folder: " + sourceFolder);
            return sourceFolder;
        } else {
            System.out.println("No folder selected.");
            return "";
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
