package com.example.iae;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String projectDir = "./src/src/test/manuelTestFolders/test_IAE/javaCompleteTest";
        //configuration
        Configuration configuration = new Configuration("javaAllTest", "java", "5", projectDir, "./src/src/test/manuelTestFolders/test_IAE/javaCompleteTest/correct_result.txt", 2);
        Compiler compiler = new Compiler();
        compiler.runAttempt(configuration);

        ScoreDocument scoreDocument = new ScoreDocument();
        scoreDocument.fillList(projectDir);

        FileOperations fileOperations = new FileOperations();
        fileOperations.createReportFile(scoreDocument, projectDir);


        File f = new File("./src/src/test/manuelTestFolders/test_IAE/javaCompleteTest");
        File[] x = f.listFiles();
        System.out.println(f.getAbsolutePath());
    }
}
