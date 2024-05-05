package com.example.iae;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration {

    private String name; //will be used as a filename "name.json"
    private String language;
    private String argument;
    private String targetFolderPath;
    private String intendedOutputFilePath;
    private int timeLimit;

    public Configuration() { //is used by ObjectMapper
    }
    public Configuration(String name, String language, String argument, String targetFolderPath, String intendedOutputFilePath, int timeLimit) {
        this.name = name;
        this.language = language;
        this.argument = argument;
        this.targetFolderPath = targetFolderPath;
        this.intendedOutputFilePath = intendedOutputFilePath;
        this.timeLimit = timeLimit;
    }

    public Configuration(String filePath) { //load configuration from .json file
        ObjectMapper mapper = new ObjectMapper();
        try {
            Configuration config = mapper.readValue(new File(filePath), Configuration.class);

            this.name = config.name;
            this.language = config.language;
            this.argument = config.argument;
            this.targetFolderPath = config.targetFolderPath;
            this.intendedOutputFilePath = config.intendedOutputFilePath;
            this.timeLimit = config.timeLimit;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getTargetFolderPath() {
        return targetFolderPath;
    }

    public void setTargetFolderPath(String targetFolderPath) {
        this.targetFolderPath = targetFolderPath;
    }

    public String getIntendedOutputFilePath() {
        return intendedOutputFilePath;
    }

    public void setIntendedOutputFilePath(String intendedOutputFilePath) {
        this.intendedOutputFilePath = intendedOutputFilePath;
    }


    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }


    public void saveConfiguration(String folderPath) {
        String fileName = this.getName() + ".json";
        String filePath = folderPath + File.separator + fileName;

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", argument='" + argument + '\'' +
                ", targetFolderPath='" + targetFolderPath + '\'' +
                ", intendedOutputFilePath='" + intendedOutputFilePath + '\'' +
                ", timeLimit=" + timeLimit +
                '}';
    }

    public static void main(String[] args) { //test
        Configuration c = new Configuration("conf1", "java", "abc cde efd abb", "targetfolderpath", "outputpath",5);
        c.saveConfiguration("./src/src/test/manuelTestFolders/test_IAE/configuration");
        Configuration c2 = new Configuration("./src/src/test/manuelTestFolders/test_IAE/configuration/conf1.json");
        System.out.println(c2.toString());
        FileOperations f = new FileOperations();
        List<String> names = f.listConfigurationNames();

        System.out.println(names);
    }
}