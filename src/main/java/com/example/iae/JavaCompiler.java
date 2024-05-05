package com.example.iae;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.*;
import java.util.Arrays;

public class JavaCompiler {

    public static void main(String[] args) { //TEST
        String targetFolderPath = "./src/src/test/manuelTestFolders/test_IAE/java code for sorting/sortArgs.java";
        String arguments = "resink transversomedial pharyngopathy postmineral myelosyphilis silverer evincement phrygium punnigram imminution environmental sleepify nope wauken indignance knotwort apocodeine escortee dogwatch eaglewood unbrotherliness mulse dermobranchiata typhic poststertorous indevout anatomicopathologic unimpenetrable hoggy urrhodin Dioecia unchapter nonumbilicate zwitterionic apportionable ferulic statefulness pharyngotonsillitis Mimulus recce mutinously reboant marshwort lupoid chromatophilic lauder nirles esthesiometer semisocial unbeing kangaroo takosis inconvertibility anesthetist rumorproof thoracoscopy euphorbium bizet song dolichocephali platemaker vesicupapular electroforming dilatingly meethelp loincloth avowably counterindicate treacliness Epigonus airmark polarography precomposition lemography Apinage Taal logology probeer randomization poditic individualize castigate Biloculina overscrub koolah weetless erased layery discontinuee anaphylatoxin unwounded personalism howitzer hexahydroxy koku reamer tonguiness microgametocyte baba ludefisk novelwright swinehull Odonata indefinable faineance nidologist supracargo beriberic betso archheart snary Viminal Pygopodidae acetylenediurein asphalt preimpress fountainlet bejel unpictorially heliophyte chimopeelagic warison antivaccinist overtwine preremove nerval bufonite eradicator turtling winrace psychographic impalpably amygdalase Octogynia brimming grist casave brazilein afluking meliceris portative unsteck Madelon barramunda optotechnics";
        JavaCompiler c = new JavaCompiler();
        try {
            c.compileAndRun(arguments,targetFolderPath,2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compileAndRun(String arguments, String targetFolderPath, int timeLimit) throws IOException {

        if (compileJavaFile(targetFolderPath)) {
            //run the compiled java class with arguments
            runJavaClass(targetFolderPath, arguments, timeLimit);
        } else {
            System.out.println("COMPILE ERROR!");

            FileWriter fw = new FileWriter(new File(new File(targetFolderPath).getParent() + "/", "output.txt"));
            fw.write("COMPILE ERROR!");
            fw.close();
        } //we can also add a time limit for the run time error.
    }

    public static boolean compileJavaFile(String sourceFilePath) {
        try {
            //get parent dir
            String studentFolder = new File(sourceFilePath).getParent();

            //convert sourceFilePath to an absolute path
            String absSourceFilePath = new File(sourceFilePath).getAbsolutePath();

            //execute the compilation command
            ProcessBuilder processBuilder = new ProcessBuilder("javac", absSourceFilePath);
            processBuilder.directory(new File(studentFolder));
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

    public static void runJavaClass(String sourceFilePath, String arguments, int timeLimitInSeconds) {
        try {
            //get the class name (without the .java extension)
            String className = sourceFilePath.substring(sourceFilePath.lastIndexOf(File.separator) + 1, sourceFilePath.lastIndexOf(".java"));

            //get the directory containing the Java file
            String sourceDir = new File(sourceFilePath).getParent();
            String[] argumentArray = arguments.split(" ");

            //create a file object representing the output file
            File outputFile = new File(sourceDir, "output.txt");

            //execute the compiled Java class with arguments
            ProcessBuilder processBuilder = new ProcessBuilder("java", className);
            processBuilder.directory(new File(sourceDir)); //set the working directory to the source directory
            processBuilder.command().addAll(Arrays.asList(argumentArray)); //add the arguments to the command
            processBuilder.redirectOutput(outputFile); //redirect output to the output file

            Process process = processBuilder.start();

            //create a separate thread to interrupt the process if it exceeds the time limit
            Thread interrupterThread = new Thread(() -> {
                try {
                    Thread.sleep(timeLimitInSeconds * 1000L); //convert seconds to milliseconds
                    //if there is a runtime error
                    process.destroy(); //interrupt the process if it exceeds the time limit

                    FileWriter fw = new FileWriter(new File(sourceDir, "output.txt"));
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