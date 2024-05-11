package com.example.iae;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}