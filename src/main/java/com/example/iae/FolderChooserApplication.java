package com.example.iae;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FolderChooserApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        File selectedFolder = directoryChooser.showDialog(primaryStage);

        if (selectedFolder != null) {
            String sourceFolder = selectedFolder.getAbsolutePath();
            System.out.println("Selected folder: " + sourceFolder);
            unzipAllInSelectedFolder(sourceFolder);

            //we can also delete the zip files after the extraction
        } else {
            System.out.println("No folder selected.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void unzipAllInSelectedFolder(String sourceFolder) {
        ZipCommands.extractAllZips(sourceFolder, sourceFolder);
    }
}
