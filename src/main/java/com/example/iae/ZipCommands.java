package com.example.iae;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipCommands {
    private static final int BUFFER_SIZE = 4096;

    public static void extractAllZips(String sourceFolder, String destinationFolder) {
        File[] files = new File(sourceFolder).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".zip")) {
                    String zipFileName = file.getName();
                    String folderName = zipFileName.substring(0, zipFileName.lastIndexOf('.'));
                    String destDirPath = destinationFolder + File.separator + folderName;
                    extractZip(file.getAbsolutePath(), destDirPath);
                }
            }
            System.out.println("Extraction completed successfully.");
        } else {
            System.out.println("Source folder is empty or does not exist.");
        }
    }

    public static void extractZip(String zipFilePath, String destDirectory) {
        try {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    File dir = new File(filePath);
                    dir.mkdir();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
            zipIn.close();
            System.out.println("Extraction completed for: " + zipFilePath);
        } catch (IOException e) {
            System.err.println("Error while extracting file: " + e.getMessage());
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = zipIn.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}
