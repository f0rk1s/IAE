package com.example.iae;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreDocument {

    public List<StudentResult> studentResultsList = new ArrayList<>();

    public ScoreDocument() {
    }
    public void fillList(String projectFolderPath) { //not tested yet

        /*traverse every single directory in project folder.
        1- if output.txt == COMPILE TIME ERROR!; text = Compiler Time Error, score = 0
        2- if output.txt == RUNTIME ERROR!; text = Run Time Error, score = 10
        3- compare output.txt with correct_result.txt, if equals: 100 text = PASSED score = 100, else : text = WRONG score = 25
         */

        File projectDirectory = new File(projectFolderPath);
        String correctResultPath = projectDirectory.getPath() + File.separator + "correct_result.txt";
        File[] studentFolders = projectDirectory.listFiles(); //includes files that are not a directory

        for (File studentFile : studentFolders) {
            if (!studentFile.isDirectory()) {
                continue;
            }
            String studentFolderPath = studentFile.getPath();
            String outputPath = studentFolderPath + File.separator + "output.txt";

            try (BufferedReader reader = new BufferedReader(new FileReader(outputPath))) {
                String output = reader.readLine();
                StudentResult studentResult;

                if (output.equals("COMPILE ERROR!")) {
                    studentResult = new StudentResult(studentFile.getName(), "Compile Error", 0);
                } else if (output.equals("RUNTIME ERROR!")) {
                    studentResult = new StudentResult(studentFile.getName(), "Runtime Error", 10);
                } else {
                    FileOperations fileOperations = new FileOperations();
                    boolean isEqual = fileOperations.areFilesEqual(outputPath, correctResultPath);
                    if (isEqual) {
                        studentResult = new StudentResult(studentFile.getName(), "Correct", 100);
                    } else {
                        studentResult = new StudentResult(studentFile.getName(), "Wrong", 25);
                    }
                }
                studentResultsList.add(studentResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class StudentResult {
        private String studentId;
        private String result;
        private int score;

        public StudentResult(String studentId, String result, int score) {
            this.studentId = studentId;
            this.result = result;
            this.score = score;
        }

        public String getStudentId() {
            return studentId;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return  "studentId='" + studentId + '\'' +
                    ", output='" + result + '\'' +
                    ", score=" + score;
        }
    }
}
