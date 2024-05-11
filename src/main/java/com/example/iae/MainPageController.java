package com.example.iae;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainPageController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button addFolderButton;
    @FXML
    private Button newProjectButton;
    @FXML
    private Button configSetButton;
    @FXML
    private Button runButton;

    @FXML
    private RadioButton onlyRun;
    @FXML
    private RadioButton compNrun;

    @FXML
    private TextArea correctResultArea;

    @FXML
    private TextField compCmdText;
    @FXML
    private TextField runCmdText;

    @FXML
    private TableColumn<?, ?> outcomeCol;
    @FXML
    private TableColumn<?, ?> scoreCol;
    @FXML
    private TableColumn<?, ?> stdIDcol;
    private Compiler compiler;

    @FXML
    public void initialize() {
        loadCorrectResult();
        addFolderFunc();
        compiler = new Compiler();
        runButtonFunc();
    }

    @FXML
    private void addFolderFunc() {
        addFolderButton.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Select Folder");

            Stage stage = (Stage) addFolderButton.getScene().getWindow();
            File selectedFolder = directoryChooser.showDialog(stage);

            if (selectedFolder != null) {
                String sourceFolder = selectedFolder.getAbsolutePath();
                System.out.println("Selected Folder: " + sourceFolder);
            } else {
                System.out.println("No Folder Selected");
            }
        });
    }

    private void loadCorrectResult() {
        String filePath = "src/src/test/manuelTestFolders/test_IAE/javaCompleteTest/correct_result.txt";

        StringBuilder content = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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
    private void runButtonFunc() { /**THERE IS AN ISSUE WITH CONFIGURATION INITIALIZATION
                                    this method is most likely to change!!! **/
        String language;
        if (onlyRun.isSelected()) {
            language = "java";
        } else if (compNrun.isSelected()) {
            language = "c";
        } else {
            return;
        }

        String command;
        if (language.equals("java")) {
            command = runCmdText.getText();
        } else {
            command = compCmdText.getText();
        }

        if (command.isEmpty()) {
            return;
        }

        Configuration config = new Configuration(language, command);

        try {
            compiler.runAttempt(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}