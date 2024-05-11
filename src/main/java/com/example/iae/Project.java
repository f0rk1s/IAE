package com.example.iae;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * A project is basically a project name and the folder path of the project. What is inside the directory is
 * student's zip files. A file with correct_result.txt will be created after pressing a button below the correct result.
 */
public class Project {

    private String name; //how the project will be shown when listing projects, must be same with the folder name
    private String folderPath; //project folder path

    public Project() {}
    public Project(String name, String folderPath) {
        this.name = name;
        this.folderPath = folderPath;
    }

    public Project(String projectName) { //load configuration from .json file
        String filePath = FileOperations.PROJECT_FOLDER_PATH + File.separator + projectName + ".json";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Project project = mapper.readValue(new File(filePath), Project.class);
            this.name = project.name;
            this.folderPath = project.folderPath;
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

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public void saveProject() {
        String fileName = this.getName() + ".json";
        String filePath = FileOperations.PROJECT_FOLDER_PATH + File.separator + fileName;

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", folderPath='" + folderPath + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Project p = new Project("project1", "somepath/comolokko");
        p.saveProject();
        Project p2 = new Project("project1");
        System.out.println(p2.toString());
        FileOperations f = new FileOperations();
        List<String> names = f.listProjectNames();

        System.out.println(names);
    }
}
