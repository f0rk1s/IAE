package com.example.iae;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        pythonSortFile();
    }

    static void completeJavaTest() throws IOException { // passed
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/javaCompleteTest";

        // unzip
        ZipCommands.extractAllZips(projectDir, projectDir);

        // configuration
        String compile = "javac FactorialCalculator.java";
        String run = "java FactorialCalculator 5";
        int timeLimit = 5;
        Configuration configuration = new Configuration("javaComplete", compile, run, timeLimit);

        // project
        Project project = new Project("factorial assignment", projectDir);

        // compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);
    }

    static void javaSortStringsFromStdInWithFile() throws IOException { // TODO: cannot read information from an input
                                                                        // file with '<'
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/java code for sorting/sortFromFile";

        // unzip
        ZipCommands.extractAllZips(projectDir, projectDir);

        // configuration
        String compile = "javac sortStdin.java";
        String run = "java sortStdin < inputs.txt";
        int timeLimit = 5;
        Configuration configuration = new Configuration("javaSortStdin", compile, run, timeLimit);

        // project
        Project project = new Project("sortStdin", projectDir);

        // compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);
    }

    static void javaSortArguments() throws IOException {
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/java code for sorting/sortFromFile";

        // unzip
        ZipCommands.extractAllZips(projectDir, projectDir);

        // configuration
        String compile = "javac sortStdin.java";
        String run = "java sortStdin < inputs.txt";
        int timeLimit = 5;
        Configuration configuration = new Configuration("javaSortStdin", compile, run, timeLimit);

        // project
        Project project = new Project("sortStdin", projectDir);

        // compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);
    }

    static void pythonSortArguments() throws IOException {
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/python sort/python sort arguments";

        // configuration
        String compile = "";
        String run = "python sort_arguments.py banana apple cherry";
        int timeLimit = 5;
        Configuration configuration = new Configuration("pySortArg", compile, run, timeLimit);

        // project
        Project project = new Project("sortArg", projectDir);

        // compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);
    }

    static void pythonSortFile() throws IOException {
        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/python sort/python sort from file";

        // configuration
        String compile = "";
        String run = "python sort_strings.py inputs.txt";
        int timeLimit = 5;
        Configuration configuration = new Configuration("pySortArg", compile, run, timeLimit);

        // project
        Project project = new Project("sortArg", projectDir);

        // compile and run
        Compiler compiler = new Compiler();
        compiler.runForAllStudentFiles(project, configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);

    }

}
