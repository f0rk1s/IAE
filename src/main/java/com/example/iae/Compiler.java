package com.example.iae;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Compiler {

    public void runForAllStudentFiles(Project project, Configuration configuration) throws IOException {

        //get student folder as a list
        String projectPath = project.getFolderPath();
        File projectFolder = new File(projectPath);
        File[] studentFiles = projectFolder.listFiles();

        for (File studentFile : studentFiles) {
            if (!studentFile.isDirectory()) {
                continue;
            }
            String targetFilePath = studentFile.getPath();
            if (configuration.getCompileCommand().equals("")) { //for scripted languages
                run(targetFilePath, configuration);
            } else { //for compiled languages
                compileAndRun(targetFilePath, configuration);
            }
        }
    }

    public void compileAndRun(String studentFilePath, Configuration configuration) throws IOException {

        if (compile(studentFilePath, configuration.getCompileCommand())) {
            //run the compiled java class with arguments
            run(studentFilePath, configuration);
        } else {
            System.out.println("COMPILE ERROR!");

            FileWriter fw = new FileWriter(new File(new File(studentFilePath) + "/", "output.txt"));
            fw.write("COMPILE ERROR!");
            fw.close();
        }
    }

    public static boolean compile(String studentFilePath, String compileCommand) {
        try {
            //execute the compilation command
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(studentFilePath));
            processBuilder.command(compileCommand.split("\\s+"));
            Process process = processBuilder.start();

            //wait for the process to finish
            int exitCode = process.waitFor();

            //check if compilation was successful
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void run(String studentFilePath, Configuration configuration) {
        try {
            int timeLimitInSeconds = configuration.getTimeLimit();

            //create a file object representing the output file
            File outputFile = new File(studentFilePath, "output.txt");

            //execute the compiled Java class with arguments
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(studentFilePath));
            processBuilder.command(configuration.getRunCommand().split("\\s+"));
            processBuilder.redirectOutput(outputFile); //redirect output to the output file

            Process process = processBuilder.start();

            //create a separate thread to interrupt the process if it exceeds the time limit
            Thread interrupterThread = new Thread(() -> {
                try {
                    Thread.sleep(timeLimitInSeconds * 1000L); //convert seconds to milliseconds
                    //if there is a runtime error
                    process.destroy(); //interrupt the process if it exceeds the time limit

                    FileWriter fw = new FileWriter(new File(studentFilePath, "output.txt"));
                    fw.write("RUNTIME ERROR!");
                    fw.close();
                    System.out.println("Process terminated due to time limit exceeded.");
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            });

            interrupterThread.start();

            // Wait for the execution process to finish
            process.waitFor();

            // Stop the interrupter thread
            interrupterThread.interrupt();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
