package com.example.iae;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainPageController {

    @FXML
    private Button mainPageButton;

    @FXML
    private Button projectButton;

    @FXML
    private Button configButton;

    @FXML
    private TextArea correctResultArea;

    @FXML
    private TableView<ScoreDocument.StudentResult> scoreTable;

    @FXML
    private TableColumn<ScoreDocument.StudentResult, String> stdIDcol;

    @FXML
    private TableColumn<ScoreDocument.StudentResult, String> outcomeCol;

    @FXML
    private TableColumn<ScoreDocument.StudentResult, Integer> scoreCol;

    @FXML
    private Button saveResultButton;

    @FXML
    private Button runButton;

    @FXML
    private TextField configCompText;
    @FXML
    private TextField configRunText;
    @FXML

    @FXML
    private void initialize() {
        loadCorrectResult();


        scoreTable.setRowFactory(tv -> new TableRow<ScoreDocument.StudentResult>() {
            @Override
            protected void updateItem(ScoreDocument.StudentResult item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if ("Compile Error".equals(item.getResult())) {
                        setStyle("-fx-background-color: red;");
                    } else if ("Runtime Error".equals(item.getResult())) {
                        setStyle("-fx-background-color: yellow;");
                    } else if ("Wrong".equals(item.getResult())) {
                        setStyle("-fx-background-color: yellow;");
                    } else if ("Correct".equals(item.getResult())) {
                        setStyle("-fx-background-color: lightgreen;");
                    }
                }
            }
        });

        readValuesFromFile("src/test/manuelTestFolders/test_IAE/javaCompleteTest/javaCompleteTest.txt");

        // Set context menus
        setContextMenuForButton(configButton, "Configuration");
        setContextMenuForButton(projectButton, "Project");
    }

    private void readValuesFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String studentId = parts[0].split("=")[1].replace("'", "").trim();
                String result = parts[1].split("=")[1].replace("'", "").trim();
                int score = Integer.parseInt(parts[2].split("=")[1].trim());
                scoreTable.getItems().add(new ScoreDocument.StudentResult(studentId, result, score));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCorrectResult() {
        String filePath = "src/test/manuelTestFolders/test_IAE/javaCompleteTest/correct_result.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        correctResultArea.setText(content.toString());
    }

    @FXML
    private void goToMainPage(ActionEvent event) {
        switchScene("/com/example/iae/main-page.fxml");
    }

    @FXML
    private void goToProjects(ActionEvent event) {
        switchScene("/com/example/iae/new-project-page.fxml");
    }

    @FXML
    private void goToConfiguration(ActionEvent event) {
        switchScene("/com/example/iae/configuration.fxml");
    }

    @FXML
    private void goToSaveResult(ActionEvent event) {
        switchScene("/com/example/iae/save-result.fxml");
    }

    @FXML
    private void runButtonFunc(ActionEvent event) {
        if (configuration == null) {
            showAlert("Configuration is not set.");
            return;
        }

        String compileCommand = configuration.getCompileCommand();
        String runCommand = configuration.getRunCommand();

        if (compileCommand == null || compileCommand.isEmpty() || runCommand == null || runCommand.isEmpty()) {
            showAlert("Compile command or Run command fields are not set.");
            return;
        }

        try {
            Compiler compiler = new Compiler();
            compiler.runForAllStudentFiles(project, configuration);

            String projectFolderPath = project.getFolderPath();
            ScoreDocument scoreDocument = new ScoreDocument();
            scoreDocument.fillList(projectFolderPath);

            FileOperations fileOperations = new FileOperations();
            fileOperations.createReportFile(scoreDocument, projectFolderPath);

            scoreTable.refresh();
            File projectFolder = new File(projectFolderPath);
            String projectName = projectFolder.getName();
            String documentPath = projectFolderPath + "/" + projectName + ".txt";
            readValuesFromFile(documentPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addConfiguration(ActionEvent event) {
        String configName = "config"; // Replace with actual input if needed
        Configuration newConfig = new Configuration(configName, configCompText.getText(), configRunText.getText(), Integer.parseInt(configTimeText.getText()));
        newConfig.saveConfiguration();
        showAlert("Configuration added successfully.");
    }

    @FXML
    private void editConfiguration(ActionEvent event) {
        if (configuration != null) {
            configuration.setCompileCommand(configCompText.getText());
            configuration.setRunCommand(configRunText.getText());
            configuration.setTimeLimit(Integer.parseInt(configTimeText.getText()));
            configuration.saveConfiguration();
            showAlert("Configuration edited successfully.");
        } else {
            showAlert("No configuration selected for editing.");
        }
    }

    @FXML
    private void deleteConfiguration(ActionEvent event) {
        if (configuration != null) {
            File configFile = new File(FileOperations.CONFIG_FOLDER_PATH + File.separator + configuration.getName() + ".json");
            if (configFile.delete()) {
                showAlert("Configuration deleted successfully.");
                configuration = null;
                clearConfigFields();
            } else {
                showAlert("Failed to delete configuration.");
            }
        } else {
            showAlert("No configuration selected for deletion.");
        }
    }

    @FXML
    private void addProject(ActionEvent event) {
        // Add your implementation for adding a project
        showAlert("Project added successfully.");
    }

    @FXML
    private void editProject(ActionEvent event) {
        // Add your implementation for editing a project
        showAlert("Project edited successfully.");
    }

    @FXML
    private void deleteProject(ActionEvent event) {
        // Add your implementation for deleting a project
        showAlert("Project deleted successfully.");
    }

    private void setContextMenuForButton(Button button, String type) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem addItem = new MenuItem("Add");
        MenuItem editItem = new MenuItem("Edit");
        MenuItem deleteItem = new MenuItem("Delete");

        addItem.setOnAction(event -> {
            if (type.equals("Configuration")) {
                addConfiguration(event);
            } else if (type.equals("Project")) {
                addProject(event);
            }
        });

        editItem.setOnAction(event -> {
            if (type.equals("Configuration")) {
                editConfiguration(event);
            } else if (type.equals("Project")) {
                editProject(event);
            }
        });

        deleteItem.setOnAction(event -> {
            if (type.equals("Configuration")) {
                deleteConfiguration(event);
            } else if (type.equals("Project")) {
                deleteProject(event);
            }
        });

        contextMenu.getItems().addAll(addItem, editItem, deleteItem);

        button.setOnAction(event -> contextMenu.show(button, Side.BOTTOM, 0, 0));
    }

    private void clearConfigFields() {
        configCompText.clear();
        configRunText.clear();
        configTimeText.clear();
    }

    private void switchScene(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFilePath));
            Parent root = loader.load();
            Stage stage = (Stage) mainPageButton.getScene().getWindow();
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
