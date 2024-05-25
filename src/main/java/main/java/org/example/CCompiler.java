package main.java.org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class CCompiler {

    public static void main(String[] args) {
        String sourceFilePath = "\"C:\\Users\\Forkis\\Desktop\\dasfadsfa\\main.c\"";
        String executableFilePath = sourceFilePath.replace(".c", ".exe"); // Assuming the output executable file will
                                                                          // have the same name with .exe extension

        // Compile the C file
        if (compileCFile(sourceFilePath, executableFilePath)) {
            // Run the compiled executable
            runExecutable(executableFilePath);
        } else {
            System.out.println("Compilation failed. Check the error messages.");
        }
    }

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

    public static void runExecutable(String executableFilePath) {
        try {
            // Execute the compiled executable
            Process process = new ProcessBuilder(executableFilePath).start();

            // Read the output of the executable
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the execution process to finish
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}