package com.mycompany.to.dolistapplication;

import javax.swing.*;
import java.awt.*;

public class ToDoGUI extends JFrame {

    private DefaultListModel<String> taskListModel; // To store task titles
    private JList<String> taskList;                // List to display task titles
    private JTextArea taskDetailsArea;             // To show task details
    private TaskManager taskManager;               // TaskManager for task operations

    public ToDoGUI() {
        // Frame setup
        setTitle("To-Do List Application");
        setSize(900, 600);  // Increased the size to 900x600
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame

        // Black-and-White Theme
        getContentPane().setBackground(Color.BLACK);

        // Initialize TaskManager
        taskManager = new TaskManager();

        // Task List Panel (Left Side)
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Arial", Font.PLAIN, 16));
        taskList.setBackground(Color.BLACK);
        taskList.setForeground(Color.WHITE);
        taskList.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JScrollPane taskListScrollPane = new JScrollPane(taskList);
        taskListScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Tasks",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(180, 180, 180))); // Darker white bold for "Tasks"
        taskListScrollPane.setPreferredSize(new Dimension(350, 0));  // Increased width to 350

        // Task Details Panel (Right Side)
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBackground(Color.BLACK);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        taskDetailsArea = new JTextArea();
        taskDetailsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        taskDetailsArea.setBackground(Color.BLACK);
        taskDetailsArea.setForeground(Color.WHITE);
        taskDetailsArea.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        taskDetailsArea.setEditable(false);

        JScrollPane detailsScrollPane = new JScrollPane(taskDetailsArea);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE),
                "Task Details",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(180, 180, 180))); // Darker white bold for "Task Details"

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark as Completed");
        JButton deleteButton = new JButton("Delete Task");
        JButton viewButton = new JButton("View Task Details");

        // Button Styling
        JButton[] buttons = {addButton, completeButton, deleteButton, viewButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Bold and larger
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);               // Black text on white button
            button.setFocusPainted(false);
        }

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);
        detailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add components to Frame
        add(taskListScrollPane, BorderLayout.WEST);
        add(detailsPanel, BorderLayout.CENTER);

        // Action Listeners
        addButton.addActionListener(e -> addTask());
        viewButton.addActionListener(e -> viewTaskDetails());
        completeButton.addActionListener(e -> markTaskAsCompleted());
        deleteButton.addActionListener(e -> deleteTask());
    }

    // Add Task
    private void addTask() {
        JTextField titleField = new JTextField(30);  // Increased width
        JTextArea descriptionArea = new JTextArea(8, 30);  // Increased size for description area
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        // Black-and-White Theme for Input Fields
        titleField.setBackground(Color.BLACK);
        titleField.setForeground(Color.WHITE);         // Normal white text
        titleField.setCaretColor(Color.WHITE);
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        titleField.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        descriptionArea.setBackground(Color.BLACK);
        descriptionArea.setForeground(Color.WHITE);    // Normal white text
        descriptionArea.setCaretColor(Color.WHITE);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);

        JPanel inputPanel = new JPanel(new BorderLayout(20, 20));  // Increased padding
        inputPanel.setBackground(Color.BLACK);

        // Title Label and Field
        JLabel titleLabel = new JLabel("Task Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Dark white bold
        titleLabel.setForeground(new Color(230, 230, 230));
        inputPanel.add(titleLabel, BorderLayout.NORTH);
        inputPanel.add(titleField, BorderLayout.CENTER);

        // Description Label and Scroll Pane
        JLabel descriptionLabel = new JLabel("Task Description:");  // Adding description label
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));  // Same bold style
        descriptionLabel.setForeground(new Color(230, 230, 230));   // Dark white bold for description label
        JPanel descriptionPanel = new JPanel(new BorderLayout(10, 10));
        descriptionPanel.setBackground(Color.BLACK);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        inputPanel.add(descriptionPanel, BorderLayout.SOUTH);

        // Loop to ensure the dialog stays open until valid input is provided
        while (true) {
            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Add New Task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String title = titleField.getText().trim();
                String description = descriptionArea.getText().trim();
                if (!title.isEmpty() && !description.isEmpty()) {
                    taskManager.addTask(title, description);
                    taskListModel.addElement(title);
                    JOptionPane.showMessageDialog(this, "Task added successfully!");
                    break; // Close the dialog once the task is added successfully
                } else {
                    JOptionPane.showMessageDialog(this, "Both fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                break; // Close the dialog when the user presses Cancel
            }
        }
    }

    // View Task Details
    private void viewTaskDetails() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String taskTitle = taskListModel.getElementAt(selectedIndex);
            for (Task task : taskManager.getAllTasks()) {
                if (task.getTitle().equalsIgnoreCase(taskTitle)) {
                    taskDetailsArea.setText("Title: " + task.getTitle() + "\nDescription: " + task.getDescription()
                            + "\nStatus: " + (task.isCompleted() ? "Completed" : "Pending"));
                    return;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to view details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mark Task as Completed
    private void markTaskAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String taskTitle = taskListModel.getElementAt(selectedIndex);
            try {
                taskManager.markTaskAsCompleted(taskTitle);
                JOptionPane.showMessageDialog(this, "Task marked as completed!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to mark as completed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete Task
    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String taskTitle = taskListModel.getElementAt(selectedIndex);
            taskListModel.remove(selectedIndex);
            taskManager.getAllTasks().removeIf(task -> task.getTitle().equalsIgnoreCase(taskTitle));
            JOptionPane.showMessageDialog(this, "Task deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to delete!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ToDoGUI().setVisible(true);
        });
    }
}
