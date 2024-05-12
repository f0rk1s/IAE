package com.example.iae;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration {

    private String name; //will be used as a filename "name.json"
    private String compileCommand;
    private String runCommand;
    private int timeLimit;

    public Configuration() { //is used by ObjectMapper
    }
    public Configuration(String name, String compileCommand, String runCommand, int timeLimit) {
        this.name = name;
        this.compileCommand = compileCommand;
        this.runCommand = runCommand;
        this.timeLimit = timeLimit;
    }

    public Configuration(String confName) { //load configuration from .json file
        String filePath = FileOperations.CONFIG_FOLDER_PATH + File.separator + confName + ".json";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Configuration config = mapper.readValue(new File(filePath), Configuration.class);
            this.name = config.name;
            this.compileCommand = config.compileCommand;
            this.runCommand = config.runCommand;
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

    public String getCompileCommand() {
        return compileCommand;
    }

    public void setCompileCommand(String compileCommand) {
        this.compileCommand = compileCommand;
    }

    public String getRunCommand() {
        return runCommand;
    }

    public void setRunCommand(String runCommand) {
        this.runCommand = runCommand;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }


    public void saveConfiguration() {
        String fileName = this.getName() + ".json";
        String filePath = FileOperations.CONFIG_FOLDER_PATH + File.separator + fileName;

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
                ", compileCommand='" + compileCommand + '\'' +
                ", runCommand='" + runCommand + '\'' +
                ", timeLimit=" + timeLimit +
                '}';
    }

    public static void main(String[] args) { //conf class works
        Configuration c = new Configuration("conf3", "javac FactorialCalculator.java", "java FactorialCalculator 5", 2);
        c.saveConfiguration();
        Configuration c3 = new Configuration("conf3");
        System.out.println(c3.toString());
        FileOperations f = new FileOperations();
        List<String> names = f.listConfigurationNames();

        System.out.println(names);
    }
}