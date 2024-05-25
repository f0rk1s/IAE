import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private DefaultTableModel projectTableModel;

    public Main() {
        setTitle("Integrated Assignment Environment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels to CardLayout
        mainPanel.add(createMainPage(), "MainPage");
        mainPanel.add(createConfigurationPage(), "ConfigurationPage");
        mainPanel.add(createProjectPage(), "ProjectPage");

        // Integrate the FileChooser and FolderChooser panels
        FileChooser fileChooser = new FileChooser();
        mainPanel.add(fileChooser.createFileChooserPanel(this), "FileChooserPage");

        FolderChooser folderChooser = new FolderChooser();
        mainPanel.add(folderChooser.createFolderChooserPanel(this), "FolderChooserPage");

        add(mainPanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private JPanel createMainPage() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create main panel content
        JPanel contentPanel = new JPanel(new GridLayout(3, 1));

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addFolderButton = createStyledButton("Add Folder", "folder.png");
        JButton newProjectButton = createStyledButton("New Project", "new_project.png");
        JButton configButton = createStyledButton("Configuration Settings", "config.png");
        buttonPanel.add(addFolderButton);
        buttonPanel.add(newProjectButton);
        buttonPanel.add(configButton);
        contentPanel.add(buttonPanel);

        // Create result area
        JTextArea resultArea = createStyledTextArea("Correct Result\nFactorial of 5 is: 120");
        contentPanel.add(new JScrollPane(resultArea));

        // Create scores table
        String[] columnNames = {"Student ID", "Outcome", "Score"};
        Object[][] data = {
                {"20160602169", "Correct", 100},
                {"20180602016", "Compile Error", 0},
                {"20190602026", "Compile Error", 0},
                {"20200602004", "Runtime Error", 10},
                {"20210602001", "Wrong", 25}
        };
        JTable scoresTable = createStyledTable(data, columnNames);
        contentPanel.add(new JScrollPane(scoresTable));

        panel.add(contentPanel, BorderLayout.CENTER);

        // Create command panel
        JPanel commandPanel = new JPanel(new GridLayout(3, 2));
        JRadioButton onlyRunButton = createStyledRadioButton("Only Run");
        JRadioButton compileAndRunButton = createStyledRadioButton("Compile and Run");
        ButtonGroup group = new ButtonGroup();
        group.add(onlyRunButton);
        group.add(compileAndRunButton);
        commandPanel.add(onlyRunButton);
        commandPanel.add(compileAndRunButton);
        JTextField compileCommandField = createStyledTextField();
        JTextField runCommandField = createStyledTextField();
        commandPanel.add(new JLabel("Compile command"));
        commandPanel.add(compileCommandField);
        commandPanel.add(new JLabel("Run command"));
        commandPanel.add(runCommandField);
        JButton runButton = createStyledButton("Run", "run.png");
        commandPanel.add(runButton);

        panel.add(commandPanel, BorderLayout.SOUTH);

        // Action listeners
        addFolderButton.addActionListener(e -> switchToPanel("FolderChooserPage"));
        newProjectButton.addActionListener(e -> switchToPanel("ProjectPage"));
        configButton.addActionListener(e -> switchToPanel("ConfigurationPage"));
        runButton.addActionListener(e -> runCommand());

        // Styling
        stylePanel(panel);

        return panel;
    }

    private JPanel createConfigurationPage() {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Compile Command"));
        JTextField compileCommandField = createStyledTextField();
        panel.add(compileCommandField);

        panel.add(new JLabel("Run Command"));
        JTextField runCommandField = createStyledTextField();
        panel.add(runCommandField);

        panel.add(new JLabel("Compile Time"));
        JTextField compileTimeField = createStyledTextField();
        panel.add(compileTimeField);

        JButton saveButton = createStyledButton("Save", "save.png");
        panel.add(saveButton);

        JButton backButton = createStyledButton("Back to Main Page", "back.png");
        panel.add(backButton);

        // Action listeners
        backButton.addActionListener(e -> switchToPanel("MainPage"));
        saveButton.addActionListener(e -> saveConfiguration());

        // Styling
        stylePanel(panel);

        return panel;
    }

    private JPanel createProjectPage() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addProjectButton = createStyledButton("Add Project", "add_project.png");
        JButton editProjectButton = createStyledButton("Edit Project", "edit_project.png");
        JButton deleteProjectButton = createStyledButton("Delete Project", "delete_project.png");
        buttonPanel.add(addProjectButton);
        buttonPanel.add(editProjectButton);
        buttonPanel.add(deleteProjectButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // Create projects table
        String[] columnNames = {"Project ID", "Project Name", "Status"};
        Object[][] data = {
                {"1", "Project 1", "Completed"},
                {"2", "Project 2", "In Progress"},
                {"3", "Project 3", "Not Started"}
        };
        projectTableModel = new DefaultTableModel(data, columnNames);
        JTable projectsTable = createStyledTable(data, columnNames);
        panel.add(new JScrollPane(projectsTable), BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to Main Page", "back.png");
        panel.add(backButton, BorderLayout.SOUTH);

        // Action listeners
        backButton.addActionListener(e -> switchToPanel("MainPage"));
        addProjectButton.addActionListener(e -> addProject());
        editProjectButton.addActionListener(e -> editProject());
        deleteProjectButton.addActionListener(e -> deleteProject());

        // Styling
        stylePanel(panel);

        return panel;
    }

    private JButton createStyledButton(String text, String iconName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Verdana", Font.BOLD, 12));
        button.setBackground(new Color(204, 255, 204)); // pastel green buttons
        button.setForeground(Color.BLACK);
        button.setIcon(new ImageIcon(getClass().getResource(iconName))); // Assumes icons are in the same package
        return button;
    }

    private JTextArea createStyledTextArea(String text) {
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setText(text);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setBackground(new Color(255, 255, 204)); // pastel yellow background
        return textArea;
    }

    private JTable createStyledTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(new DefaultTableModel(data, columnNames));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(new Color(255, 255, 255)); // white background
        table.setGridColor(new Color(204, 204, 255)); // pastel purple grid
        return table;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(255, 255, 255)); // white background
        return textField;
    }

    private JRadioButton createStyledRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
        radioButton.setBackground(new Color(255, 230, 230)); // pastel pink background
        return radioButton;
    }

    public void switchToPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    private void addFolder() {
        // Implement add folder functionality
    }

    private void newProject() {
        switchToPanel("ProjectPage");
    }

    private void saveConfiguration() {
        // Implement save configuration functionality
    }

    private void addProject() {
        // Example implementation for adding a project
        String projectId = JOptionPane.showInputDialog("Enter Project ID:");
        String projectName = JOptionPane.showInputDialog("Enter Project Name:");
        String status = JOptionPane.showInputDialog("Enter Project Status:");
        projectTableModel.addRow(new Object[]{projectId, projectName, status});
    }

    private void editProject() {
        // Example implementation for editing a project
        int selectedRow = JOptionPane.showConfirmDialog(this, "Edit the selected project?", "Edit Project", JOptionPane.OK_CANCEL_OPTION);
        if (selectedRow != -1) {
            String projectId = JOptionPane.showInputDialog("Edit Project ID:", projectTableModel.getValueAt(selectedRow, 0));
            String projectName = JOptionPane.showInputDialog("Edit Project Name:", projectTableModel.getValueAt(selectedRow, 1));
            String status = JOptionPane.showInputDialog("Edit Project Status:", projectTableModel.getValueAt(selectedRow, 2));
            projectTableModel.setValueAt(projectId, selectedRow, 0);
            projectTableModel.setValueAt(projectName, selectedRow, 1);
            projectTableModel.setValueAt(status, selectedRow, 2);
        }
    }

    private void deleteProject() {
        // Example implementation for deleting a project
        int selectedRow = JOptionPane.showConfirmDialog(this, "Delete the selected project?", "Delete Project", JOptionPane.OK_CANCEL_OPTION);
        if (selectedRow != -1) {
            projectTableModel.removeRow(selectedRow);
        }
    }

    private void runCommand() {
        // Implement run command functionality
    }

    public void stylePanel(JPanel panel) {
        panel.setBackground(new Color(255, 230, 230)); // pastel pink background
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(new Color(102, 102, 153)); // pastel purple text
            } else if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(new Color(204, 255, 204)); // pastel green buttons
                button.setForeground(Color.BLACK);
            }
        }
    }

    public void styleButton(JButton button) {
        button.setFont(new Font("Verdana", Font.BOLD, 12));
        button.setBackground(new Color(204, 255, 204)); // pastel green buttons
        button.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
