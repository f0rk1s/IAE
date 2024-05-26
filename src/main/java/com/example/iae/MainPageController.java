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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class MainPageController {

    private Project project;
    private Configuration configuration;

    @FXML
    private Label welcomeText;

    private Stage stage;
    private Scene scene;
    private Parent root;

    //For New Project Page
    @FXML
    private Button createProjectButton;

    @FXML
    private Button searchProjectFolder;

    @FXML
    private TextField newProjectNameText;

    @FXML
    private TextField projectConfigPathText;

    @FXML
    private TextField projectFoldPathText;

    //Configuration Settings Page
    @FXML
    private TextField configCompText;

    @FXML
    private TextField configRunText;

    @FXML
    private Button configSaveButton;

    @FXML
    private TextField configTimeText;

    @FXML
    private Button delConfigButton;

    @FXML
    private Button editConfigButton;

    @FXML
    private Button loadConfigButton;

    @FXML
    private Button newConfigButton;

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
    private TableView<ScoreDocument.StudentResult> scoreTable = new TableView<>();

    @FXML
    private TableColumn<ScoreDocument.StudentResult, String> outcomeCol;
    @FXML
    private TableColumn<ScoreDocument.StudentResult, Integer> scoreCol;
    @FXML
    private TableColumn<ScoreDocument.StudentResult, String> stdIDcol;
    private Compiler compiler;
    public MainPageController controller;

    public void readValuesFromFile(String filePath) {
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
        newProjectButton.setOnAction(event -> openNewProject());

        System.out.println("initialize() method called");

        stdIDcol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        outcomeCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreTable.refresh();

        String filePath = "src/src/test/manuelTestFolders/test_IAE/javaCompleteTest/javaCompleteTest.txt";
        readValuesFromFile(filePath);

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
    public void openConfigSettings() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("deneme.fxml"));
            Scene scene = new Scene(loader.load(), 361, 400);
            this.controller = loader.getController();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("Configuration Settings");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveConfiguration() {
        String name = "configuration";  // You might want to get this from another TextField or a prompt
        String compileCommand = configCompText.getText();
        String runCommand = configRunText.getText();
        int timeLimit = Integer.parseInt(configTimeText.getText());

        Configuration config = new Configuration(name, compileCommand, runCommand, timeLimit);
        config.saveConfiguration();
    }

    @FXML
    private void editConfiguration() {
        String name = "configuration";  // You might want to get this from another TextField or a prompt
        Configuration config = new Configuration(name);
        
        config.setCompileCommand(configCompText.getText());
        config.setRunCommand(configRunText.getText());
        config.setTimeLimit(Integer.parseInt(configTimeText.getText()));
        
        config.saveConfiguration();
    }

    @FXML
    private void deleteConfiguration() {
        String name = "configuration";  // You might want to get this from another TextField or a prompt
        String filePath = FileOperations.CONFIG_FOLDER_PATH + File.separator + name + ".json";
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        } else {
            System.out.println("Configuration file not found.");
        }
    }

    @FXML
    private void loadConfiguration() {
        String name = "configuration";  // You might want to get this from another TextField or a prompt
        Configuration config = new Configuration(name);
        configCompText.setText(config.getCompileCommand());
        configRunText.setText(config.getRunCommand());
        configTimeText.setText(String.valueOf(config.getTimeLimit()));
    }

    @FXML
    private void newConfiguration() {
        configCompText.clear();
        configRunText.clear();
        configTimeText.clear();
    }

    @FXML
    private void openNewProject() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-new-project-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 490, 337);
            stage.setResizable(false);
            NewProjectController newProjectController = fxmlLoader.getController();
            stage.setTitle("New Project");
            stage.setScene(scene);
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

        if (configuration == null) {
            System.out.println("!!");
            return;
        }

        String compileCommand = configuration.getCompileCommand();
        String runCommand = configuration.getRunCommand();


        if (compileCommand == null || compileCommand.isEmpty() || runCommand == null || runCommand.isEmpty()) {
            System.out.println("Compile command or Run command fields are not set.");
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

    private void fillTable() {
        stdIDcol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        outcomeCol.setCellValueFactory(new PropertyValueFactory<>("result"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        //scoreTable.refresh(); // Refresh the table view
    }

    //TODO: Project creation

    @FXML
    private void newProjectPage_createProjectButtonAction() {
        String projectName = newProjectNameText.getText().trim();

        //project folder path
        String projectFolderPath = projectFoldPathText.getText().trim();

        Project p = new Project(projectName, projectFolderPath); //our project

        String confName = projectConfigPathText.getText(); //conf name
        Configuration conf = new Configuration(confName); //our configuration

        project = p;
        configuration = conf;
    }

    @FXML
    private void searchForConfigurationAction() {
        Stage stage = new Stage();

        // Call the showFileChooser method from the FileChooserApplication class
        String selectedFilePath = FileChooserApplication.showFileChooser(stage);
        //

        // Do something with the selected file path
        System.out.println("Selected file path from another class: " + selectedFilePath);
    }

    @FXML
    private void NewProjectButtonAction() {

    }

    @FXML
    private void searchForProjectFolder() {
        Stage stage = new Stage();

        // Call the showFolderChooser method from the FolderChooserApplication class
        String projectFolderPath = FolderChooserApplication.showFolderChooser(stage);
        projectFoldPathText.setText(projectFolderPath);

        // Do something with the selected folder path
        System.out.println("Selected folder path from another class: " + projectFolderPath);
    }
   

    @FXML
    private void saveFilesToDesktop() {
        try {
            String desktopPath = System.getProperty("user.home") + "/Desktop/";
            
            // Save MainPageController.java
            String javaContent = // (The complete Java code as a string);
            BufferedWriter writer = new BufferedWriter(new FileWriter(desktopPath + "MainPageController.java"));
            writer.write(javaContent);
            writer.close();

            // Save the FXML content
            String fxmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                                + "<?import javafx.scene.control.Button?>\n"
                                + "<?import javafx.scene.control.Label?>\n"
                                + "<?import javafx.scene.control.TextField?>\n"
                                + "<?import javafx.scene.layout.AnchorPane?>\n"
                                + "\n"
                                + "<AnchorPane maxHeight=\"-Infinity\" maxWidth=\"-Infinity\" minHeight=\"-Infinity\" minWidth=\"-Infinity\" prefHeight=\"400.0\" prefWidth=\"393.0\" xmlns=\"http://javafx.com/javafx/21\" xmlns:fx=\"http://javafx.com/fxml/1\" fx:controller=\"com.example.iae.MainPageController\">\n"
                                + "   <children>\n"
                                + "      <TextField id=\"ConfigRunText\" fx:id=\"configRunText\" layoutX=\"68.0\" layoutY=\"222.0\" prefHeight=\"26.0\" prefWidth=\"246.0\" />\n"
                                + "      <TextField id=\"ConfigTimeText\" fx:id=\"configTimeText\" layoutX=\"69.0\" layoutY=\"286.0\" prefHeight=\"26.0\" prefWidth=\"246.0\" />\n"
                                + "      <TextField id=\"configCompText\" fx:id=\"configCompText\" layoutX=\"68.0\" layoutY=\"160.0\" prefHeight=\"26.0\" prefWidth=\"245.0\" />\n"
                                + "      <Label layoutX=\"138.0\" layoutY=\"133.0\" text=\"Compile Command\" />\n"
                                + "      <Label layoutX=\"150.0\" layoutY=\"198.0\" text=\"Run Command\" />\n"
                                + "      <Label layoutX=\"152.0\" layoutY=\"261.0\" text=\"Compile Time\" />\n"
                                + "      <Button id=\"ConfigSaveButton\" fx:id=\"configSaveButton\" layoutX=\"265.0\" layoutY=\"348.0\" mnemonicParsing=\"false\" prefHeight=\"34.0\" prefWidth=\"79.0\" text=\"Save\" onAction=\"#saveConfiguration\" />\n"
                                + "      <Button id=\"ConfigSaveButton\" fx:id=\"editConfigButton\" layoutX=\"46.0\" layoutY=\"48.0\" mnemonicParsing=\"false\" prefHeight=\"26.0\" prefWidth=\"133.0\" text=\"Edit Configuration\" onAction=\"#editConfiguration\" />\n"
                                + "      <Button id=\"ConfigSaveButton\" fx:id=\"delConfigButton\" layoutX=\"46.0\" layoutY=\"82.0\" mnemonicParsing=\"false\" prefHeight=\"26.0\" prefWidth=\"134.0\" text=\"Delete Configuration\" onAction=\"#deleteConfiguration\" />\n"
                                + "      <Button fx:id=\"loadConfigButton\" layoutX=\"200.0\" layoutY=\"48.0\" mnemonicParsing=\"false\" prefHeight=\"26.0\" prefWidth=\"134.0\" text=\"Load Configuration\" onAction=\"#loadConfiguration\" />\n"
                                + "      <Button id=\"ConfigSaveButton\" fx:id=\"newConfigButton\" layoutX=\"200.0\" layoutY=\"82.0\" mnemonicParsing=\"false\" prefHeight=\"26.0\" prefWidth=\"134.0\" text=\"New Configuration\" onAction=\"#newConfiguration\" />\n"
                                + "   </children>\n"
                                + "</AnchorPane>";
            BufferedWriter writer2 = new BufferedWriter(new FileWriter(desktopPath + "configurations.fxml"));
            writer2.write(fxmlContent);
            writer2.close();

            System.out.println("Files have been saved to the desktop.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
