package com.mycompany.to.dolistapplication;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    public LoginGUI() {
        // Frame Title and Size
        setTitle("To-Do List Application - Login");
        setSize(500, 400); // Increased frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame on the screen

        // Panel for Form
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Add padding around the form
        loginPanel.setBackground(Color.BLACK); // Black background

        // Title Label
        JLabel titleLabel = new JLabel("To-Do List Application Login", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE); // White text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add space below the title
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        // Form to Login
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 15, 15)); // Adjusted layout for better spacing
        formPanel.setBackground(Color.BLACK);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // White text
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // White text

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Set Field Sizes and Styles
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // White border
        usernameField.setBackground(Color.BLACK); // Black background
        usernameField.setForeground(Color.WHITE); // White text
        usernameField.setCaretColor(Color.WHITE); // White cursor

        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // White border
        passwordField.setBackground(Color.BLACK); // Black background
        passwordField.setForeground(Color.WHITE); // White text
        passwordField.setCaretColor(Color.WHITE); // White cursor

        // Set Button Style
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(Color.WHITE); // White background
        loginButton.setForeground(Color.BLACK); // Black text
        loginButton.setFocusPainted(false);

        // Center Fields with Space Between Frame and Input Fields
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Color.BLACK);
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.BLACK);
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        formPanel.add(usernamePanel);
        formPanel.add(passwordPanel);
        formPanel.add(new JLabel()); // Spacer
        formPanel.add(loginButton);

        loginPanel.add(formPanel, BorderLayout.CENTER);

        // Add Components
        add(loginPanel, BorderLayout.CENTER);

        // Button Action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Check for Empty Fields
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Check for Incorrect Username and Password
            else if (!username.equals("admin") || !password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Incorrect Username or Password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // Successful Login
            else {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                openToDoGUI();
            }
        });
    }

    private void openToDoGUI() {
        // Close the login window
        this.dispose();

        // Open the To-Do List GUI
        SwingUtilities.invokeLater(() -> {
            ToDoGUI toDoGUI = new ToDoGUI();
            toDoGUI.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}
