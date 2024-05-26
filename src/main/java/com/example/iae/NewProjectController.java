package com.example.iae;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.nio.file.*;
import java.io.File;
import java.io.IOException;


public class NewProjectController {

    @FXML
    private TextField newProjectNameText;

    @FXML
    private TextField projectFoldPathText;

    @FXML
    private TextField projectConfigPathText;

    @FXML
    private Button createProjectButton;

    @FXML
    private Button searchProjectFolder;

    @FXML
    private Button searchProjectConfig;

    @FXML
    private Button addConfigButton;



    @FXML
    private Button searchForProjectFolderButton;

    private String selectedProjectFolderPath;

    @FXML
    private void searchForProjectFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedFolder = directoryChooser.showDialog(null);
        if (selectedFolder != null && selectedFolder.isDirectory()) {
            projectFoldPathText.setText(selectedFolder.getAbsolutePath());
        } else {
            System.out.println("No folder selected or folder is invalid.");
        }
    }

    @FXML
    private void searchProjectConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File configFile = fileChooser.showOpenDialog(null);
        if (configFile != null && configFile.isFile()) {
            projectConfigPathText.setText(configFile.getAbsolutePath());
        } else {
            System.out.println("No configuration file selected or file is invalid.");
        }
    }

    @FXML
    private void createProject() {
        String projectName = newProjectNameText.getText().trim();
        String projectFolderPath = projectFoldPathText.getText().trim();
        String projectConfigPath = projectConfigPathText.getText().trim();

        if (!projectName.isEmpty() && !projectFolderPath.isEmpty() && !projectConfigPath.isEmpty()) {
            // Define the base path for project folders within your IntelliJ IDEA project
            String baseProjectPath = "src/Projects";

            // Create the project folder with the user-entered name
            File projectFolder = new File(baseProjectPath, projectName);
            if (!projectFolder.exists() && projectFolder.mkdirs()) {
                try {
                    // Copy the selected configuration file to the project folder
                    Files.copy(Paths.get(projectConfigPath), Paths.get(projectFolder.getAbsolutePath(), "config.json"), StandardCopyOption.REPLACE_EXISTING);

                    // Copy the contents of the selected folder to the project folder
                    File selectedFolder = new File(projectFolderPath);
                    for (File file : selectedFolder.listFiles()) {
                        Path sourceFilePath = file.toPath();
                        Path destFilePath = Paths.get(projectFolder.getAbsolutePath(), file.getName());
                        Files.copy(sourceFilePath, destFilePath, StandardCopyOption.REPLACE_EXISTING);
                    }

                    // Optionally, copy the folder recursively
                    copyFolderContents(selectedFolder, projectFolder);

                    // Optionally, close the new project window after creation
                    Stage stage = (Stage) createProjectButton.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    System.out.println("Failed to copy files: " + e.getMessage());
                }
            } else {
                System.out.println("Project folder already exists.");
            }
        } else {
            System.out.println("Please fill in all fields.");
        }
    }



    private static void copyFolderContents(File sourceFolder, File destinationFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }

            String[] files = sourceFolder.list();
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File destFile = new File(destinationFolder, file);

                // Recursive copy
                copyFolderContents(srcFile, destFile);
            }
        } else {
            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    file.delete();
                }
            }
        }
        folder.delete();
    }


}
