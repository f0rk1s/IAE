package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Map<String, JPanel> panels;

    public Main() {
        setTitle("Integrated Assignment Environment");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        panels = new HashMap<>();

        // Initialize panels
        JPanel fileChooserPanel = new org.example.FileChooser().createFileChooserPanel(this);
        JPanel folderChooserPanel = new org.example.FolderChooser().createFolderChooserPanel(this);
        JPanel mainPagePanel = createMainPagePanel();

        // Add panels to CardLayout
        addPanel("FileChooser", fileChooserPanel);
        addPanel("FolderChooser", folderChooserPanel);
        addPanel("MainPage", mainPagePanel);

        // Set main panel
        add(mainPanel, BorderLayout.CENTER);

        // Display the main page initially
        switchToPanel("MainPage");
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void stylePanel(JPanel panel) {
        panel.setBackground(new Color(44, 44, 84)); // Dark purple
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public void styleButton(JButton button) {
        button.setBackground(new Color(44, 44, 84)); // Dark purple
        button.setForeground(new Color(255, 215, 0)); // Yellow
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(44, 44, 84)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

    private void addPanel(String name, JPanel panel) {
        panels.put(name, panel);
        mainPanel.add(panel, name);
    }

    private JPanel createMainPagePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to the Main Page");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);

        JButton fileChooserButton = new JButton("Go to File Chooser");
        fileChooserButton.addActionListener(e -> switchToPanel("FileChooser"));
        styleButton(fileChooserButton);

        JButton folderChooserButton = new JButton("Go to Folder Chooser");
        folderChooserButton.addActionListener(e -> switchToPanel("FolderChooser"));
        styleButton(folderChooserButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(fileChooserButton);
        buttonPanel.add(folderChooserButton);
        stylePanel(buttonPanel);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        stylePanel(panel);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setVisible(true);
        });
    }
}
