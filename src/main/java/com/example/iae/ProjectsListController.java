package com.example.iae;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ProjectsListController {

    @FXML
    private TableView<Project> projectsTable;

    @FXML
    private TableColumn<Project, String> projectNameCol;

    @FXML
    private TableColumn<Project, String> projectPathText;

    @FXML
    private TableView<File> assignmentTable;

    @FXML
    private TableColumn<File, String> assignmentFolders;

    @FXML
    private TextField stdIDprojects;

    @FXML
    private TextArea assignTextArea;

    @FXML
    private TextField outProjectText;

    @FXML
    private TextField scoreProjectsText;

    private final ObservableList<Project> projects = FXCollections.observableArrayList();
    private final ObservableList<File> assignmentFoldersList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        projectNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        projectPathText.setCellValueFactory(new PropertyValueFactory<>("folderPath"));

        projectsTable.setItems(projects);
        projectsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadAssignmentFolders(newValue);
            }
        });

        assignmentFolders.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        assignmentTable.setItems(assignmentFoldersList);
        assignmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayAssignmentDetails(newValue);
            }
        });

        loadProjects();
    }

    private void loadProjects() {
        // Load projects from the directory (example implementation)
        File projectsDir = new File("src/Projects");
        if (projectsDir.exists() && projectsDir.isDirectory()) {
            for (File projectFolder : projectsDir.listFiles()) {
                if (projectFolder.isDirectory()) {
                    projects.add(new Project(projectFolder.getName(), projectFolder.getAbsolutePath()));
                }
            }
        }
    }

    private void loadAssignmentFolders(Project project) {
        assignmentFoldersList.clear();
        File projectFolder = new File(project.getFolderPath());
        if (projectFolder.exists() && projectFolder.isDirectory()) {
            for (File folder : projectFolder.listFiles()) {
                if (folder.isDirectory()) {
                    assignmentFoldersList.add(folder);
                }
            }
        }
    }


    private int calculateScore(String outcome) {
        if (outcome.contains("correct")) {
            return 100;
        } else if (outcome.contains("runtime error")) {
            return 10;
        } else if (outcome.contains("compile error")) {
            return 0;
        } else {
            return 25;
        }
    }

    private void displayAssignmentDetails(File folder) {
        stdIDprojects.setText(folder.getName());

        // Read assignment Java file content
        File[] javaFiles = folder.listFiles((dir, name) -> name.endsWith(".java"));
        if (javaFiles != null && javaFiles.length > 0) {
            try {
                assignTextArea.setText(new String(Files.readAllBytes(javaFiles[0].toPath())));
            } catch (IOException e) {
                e.printStackTrace();
                assignTextArea.setText("");
            }
        }

        // Read outcome text file content
        File outcomeFile = new File(folder, "output.txt");
        if (outcomeFile.exists()) {
            try {
                outProjectText.setText(new String(Files.readAllBytes(outcomeFile.toPath())));
                int score = calculateScore(outProjectText.getText());
                scoreProjectsText.setText(String.valueOf(score));
            } catch (IOException e) {
                e.printStackTrace();
                outProjectText.setText("");
            }
        }
    }
}
