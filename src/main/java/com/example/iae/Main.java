package com.example.iae;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        javaSortTest();
    }

    static void completeJavaTest() throws IOException {
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/javaCompleteTest";

        //unzip
        ZipCommands.extractAllZips(projectDir, projectDir);

        //configuration
        String compile = "javac FactorialCalculator.java";
        String run = "java FactorialCalculator 5";
        int timeLimit = 5;
        Configuration configuration = new Configuration("javaComplete", compile, run, timeLimit);

        //project
        Project project = new Project("factorial assignment", projectDir);

        //compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);
    }

    static void javaSortTest() throws IOException {
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/java code for sorting";

        //unzip
        ZipCommands.extractAllZips(projectDir, projectDir);

        //configuration
        String compile = "javac sortStdin.java";
        String run = "java sortStdin < inputs.txt";
        int timeLimit = 5;
        Configuration configuration = new Configuration("javaSortStdin", compile, run, timeLimit);

        //project
        Project project = new Project("sortStdin", projectDir);

        //compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);
    }
}
