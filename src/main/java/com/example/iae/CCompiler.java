package com.example.iae;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class CCompiler {

    public static void main(String[] args) {
        String sourceFilePath = "./src/src/test/manuelTestFolders/test_IAE/c code for sorting/sortArgs.c"; //replace with the path to your C file
        String arguments = "abc cde aab dfg zzz";

        //compile the C file
        if (compileCFile(sourceFilePath)) {
            //run the compiled executable with arguments
            runExecutable(sourceFilePath.replace(".c", ".exe"), arguments);
        } else {
            System.out.println("COMPILE ERROR!");
        }
    }

    public static boolean compileCFile(String sourceFilePath) {
        try {
            //convert sourceFilePath to an absolute path
            String absSourceFilePath = new File(sourceFilePath).getAbsolutePath();
            //execute the compilation command
            ProcessBuilder processBuilder = new ProcessBuilder("gcc", absSourceFilePath, "-o", absSourceFilePath.replace(".c", ".exe"));
            processBuilder.directory(new File(System.getProperty("user.dir"))); // Set the working directory
            Process process = processBuilder.start();

            //wait for the compilation process to finish
            int exitCode = process.waitFor();

            //check if compilation was successful
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void runExecutable(String executableFilePath, String arguments) {
        try {
            String[] argumentArray = arguments.split(" "); //split from whitespace

            ProcessBuilder processBuilder = new ProcessBuilder(executableFilePath);
            processBuilder.directory(new File(System.getProperty("user.dir"))); //set the working directory
            processBuilder.command().addAll(Arrays.asList(argumentArray)); //add the arguments to the command
            Process process = processBuilder.start();

            //read the output of the executable
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            //wait for the execution process to finish
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
