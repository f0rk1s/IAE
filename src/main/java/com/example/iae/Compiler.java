package com.example.iae;

import java.io.File;
import java.io.IOException;

public class Compiler {

    public Configuration configuration;


    public void runAttempt(Configuration c) throws IOException {
        String language = c.getLanguage();

        if (language.equalsIgnoreCase("c")) {
            CCompiler cCompiler = new CCompiler();

        } else if (language.equalsIgnoreCase("java")) { //create "output.txt" files for all student folders
            JavaCompiler javaCompiler = new JavaCompiler();

            //get student folder as a list
            String projectPath = c.getTargetFolderPath();
            File projectFolder = new File(projectPath);
            File[] studentFiles = projectFolder.listFiles();

            for (File studentFile : studentFiles) {
                if (!studentFile.isDirectory()) {
                    continue;
                }
                String targetFilePath = studentFile.getPath() + File.separator + "FactorialCalculator.java"; //TODO: fix name
                javaCompiler.compileAndRun(c.getArgument(), targetFilePath, c.getTimeLimit());
            }

        } else if (language.equalsIgnoreCase("python")) {
            //python compiler
        }
    }
}
