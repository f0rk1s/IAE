package com.example.iae;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.example.iae.Main.completeJavaTest;

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
    private TableView<ScoreDocument.StudentResult> scoreTable = new TableView<ScoreDocument.StudentResult>();;

    @FXML
    private TableColumn<ScoreDocument.StudentResult, String> outcomeCol;
    @FXML
    private TableColumn<ScoreDocument.StudentResult, Integer> scoreCol;
    @FXML
    private TableColumn<ScoreDocument.StudentResult, String> stdIDcol;
    private Compiler compiler;

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


    @FXML
    public void initialize() {
        loadCorrectResult();
        addFolderFunc();
        compiler = new Compiler();
        runButtonFunc();
        configSetButton.setOnAction(event -> openConfigSettings());

        System.out.println("initialize() method called");
        stdIDcol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        outcomeCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreTable.refresh();
        readValuesFromFile("src/src/test/manuelTestFolders/test_IAE/javaCompleteTest/javaCompleteTest.txt");

        scoreTable.setRowFactory(tv -> new TableRow<ScoreDocument.StudentResult>() {
            @Override
            protected void updateItem(ScoreDocument.StudentResult item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getScore() == 100) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else if (item.getScore() == 0) {
                        setStyle("-fx-background-color: red;");
                    } else {
                        setStyle("-fx-background-color: yellow;");
                    }
                }
            }
        });
    }

    @FXML
    private void openConfigSettings() { //NOT WORKING WHEN I CLICK ON CONFIGURATION SETTINGS - WHY?
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("config-page.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    private void runButtonFunc() {
        String compileCommand = compCmdText.getText().trim();
        String runCommand = runCmdText.getText().trim();

        if (compileCommand.isEmpty() || runCommand.isEmpty()) {

            System.out.println("To Proceed with running the program,\n fill in the required compile/run fields!");
            return;
        }

        try {
            String projectDir = "./src/src/test/manuelTestFolders/test_IAE/javaCompleteTest";

            // Unzip
            ZipCommands.extractAllZips(projectDir, projectDir);

            // Configuration
            Configuration configuration = new Configuration("javaComplete", compileCommand, runCommand, 5); // Assuming time limit is 5

            // Project
            Project project = new Project("factorial assignment", projectDir);

            // Compile and run
            Compiler compiler = new Compiler();
            compiler.runForAllStudentFiles(project, configuration);


            readValuesFromFile("src/src/test/manuelTestFolders/test_IAE/javaCompleteTest/javaCompleteTest.txt");
            scoreTable.refresh();

            ScoreDocument scoreDocument = new ScoreDocument();
            scoreDocument.fillList(projectDir);

            FileOperations fileOperations = new FileOperations();
            fileOperations.createReportFile(scoreDocument, projectDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillTable() {
        stdIDcol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        outcomeCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        //scoreTable.refresh(); // Refresh the table view
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}