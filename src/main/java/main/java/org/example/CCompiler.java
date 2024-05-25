package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CCompiler {

    public static boolean compileCFile(String sourceFilePath, String executableFilePath) {
        try {
            // Execute the compilation command
            ProcessBuilder processBuilder = new ProcessBuilder("gcc", sourceFilePath, "-o", executableFilePath);
            processBuilder.directory(new File(System.getProperty("user.dir"))); // Set the working directory
            Process process = processBuilder.start();

            // Wait for the compilation process to finish
            int exitCode = process.waitFor();

            // Check if compilation was successful
            return exitCode == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String runExecutable(String executableFilePath) {
        StringBuilder output = new StringBuilder();
        try {
            // Execute the compiled executable
            Process process = new ProcessBuilder(executableFilePath).start();

            // Read the output of the executable
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the execution process to finish
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
