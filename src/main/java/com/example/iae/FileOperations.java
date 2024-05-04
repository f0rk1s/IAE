package com.example.iae;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOperations {

    private static final String CONFIG_FOLDER_PATH = "C:\\Users\\Forkis\\Desktop\\test_IAE\\configuration"; //folder path of configurations folder.

    public List<String> listConfigurationNames() {
        List<String> configurationNames = new ArrayList<>();

        //get the directory for configurations
        File configFolder = new File(CONFIG_FOLDER_PATH);


        if (configFolder.exists() && configFolder.isDirectory()) { //both  boolean exp. are redundant after setting up the file structure
            File[] files = configFolder.listFiles();

            for (File file : files) {
                if (file.getName().endsWith(".json")) {
                    configurationNames.add(file.getName());
                }
            }
        }

        return configurationNames;
    }
    public boolean areFilesEqual(String filePath1, String filePath2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();

            while (line1 != null && line2 != null) {
                if (!line1.equals(line2)) {
                    return false;
                }
                line1 = reader1.readLine();
                line2 = reader2.readLine();
            }

            //if both files have same number of lines return true
            return (line1 == null && line2 == null);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createReportFile(ScoreDocument scoreDocument, String directoryPath) { //not tested

        File dir = new File(directoryPath);
        String projectName = dir.getName();
        String reportPath = directoryPath + "/" + projectName + ".txt";

        List<ScoreDocument.StudentResult> infoList = scoreDocument.studentResultsList;

        try {
            FileWriter writer = new FileWriter(reportPath);

            for (ScoreDocument.StudentResult studentInfo : infoList) {
                String line = studentInfo.toString();
                writer.write(line + "\n");
            }

            writer.close();

            System.out.println("The data has been written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
