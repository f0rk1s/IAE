package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileChooser extends JPanel {

    private JTextArea log;
    private JFileChooser fc;

    public JPanel createFileChooserPanel(Main mainFrame) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create the log first, because the action listeners need to refer to it.
        log = new JTextArea(5, 20);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        // Create a file chooser
        fc = new JFileChooser();

        // Create the open button.
        JButton openButton = new JButton("Open a File...");
        openButton.addActionListener(e -> {
            int returnVal = fc.showOpenDialog(mainFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Opening: " + file.getName() + "." + "\n");
            } else {
                log.append("Open command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });

        // Create the save button.
        JButton saveButton = new JButton("Save a File...");
        saveButton.addActionListener(e -> {
            int returnVal = fc.showSaveDialog(mainFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                log.append("Saving: " + file.getName() + "." + "\n");
            } else {
                log.append("Save command cancelled by user." + "\n");
            }
            log.setCaretPosition(log.getDocument().getLength());
        });

        // For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); // use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        JButton backButton = new JButton("Back to Main Page");
        buttonPanel.add(backButton);
        backButton.addActionListener(e -> mainFrame.switchToPanel("MainPage"));

        // Add the buttons and the log to this panel.
        panel.add(buttonPanel, BorderLayout.PAGE_START);
        panel.add(logScrollPane, BorderLayout.CENTER);

        // Styling
        mainFrame.stylePanel(panel);
        mainFrame.styleButton(openButton);
        mainFrame.styleButton(saveButton);
        mainFrame.styleButton(backButton);

        return panel;
    }
}
