package com.example.iae;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewConfigPageController {

    @FXML
    private TextField configCompText;

    @FXML
    private TextField configRunText;

    @FXML
    private TextField configTimeText;

    @FXML
    private Button saveButton;

    public NewConfigPageController controller;



    @FXML
    private void saveNewConfiguration() {
        String compileCommand = configCompText.getText();
        String runCommand = configRunText.getText();
        String timeLimitText = configTimeText.getText();


        if (compileCommand.isEmpty() || runCommand.isEmpty() || timeLimitText.isEmpty()) {

            System.out.println("One or more fields are empty. Please fill in all fields.");
            return;
        }

        try {
            int timeLimit = Integer.parseInt(timeLimitText);

        } catch (NumberFormatException e) {

            System.out.println("Invalid time limit. Please enter a valid integer value.");
        }

        closeWindow();
    }



    private void closeWindow() {

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public void receiveCommands(String compileCommand, String runCommand, int timeLimit) {
        configCompText.setText(compileCommand);
        configRunText.setText(runCommand);
        configTimeText.setText(String.valueOf(timeLimit));
    }
}
