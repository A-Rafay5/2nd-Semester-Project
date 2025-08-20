package com.mycompany.librarymanagementsystem;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    public LoginGUI() {
        // Frame Title and Size
        setTitle("Library Management System - Login");
        setSize(500, 400); // Increased frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame on the screen

        // Panel for Form
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Add padding around the form

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to the Library Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0)); // Add space below the title
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        // Form to Login
        JPanel formPanel = new JPanel(new GridLayout(5, 1, 15, 15)); // Adjusted layout for better spacing
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        // Add Tooltips for Fields
        usernameField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");

        // Set Field Sizes and Styles
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(300, 40)); // Increased height
        usernameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        usernameField.setMargin(new Insets(5, 10, 5, 10)); // Added internal padding

        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(300, 40)); // Increased height
        passwordField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        passwordField.setMargin(new Insets(5, 10, 5, 10)); // Added internal padding

        // Set Button Style
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(150, 50)); // Increased size
        loginButton.setBackground(new Color(51, 153, 255)); // Light Blue Background
        loginButton.setForeground(Color.WHITE); // White Text
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2)); // Border for button

        // Center Fields with Space Between Frame and Input Fields
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout());
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
                new LibraryManagementGUI().setVisible(true);
                this.dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginGUI().setVisible(true);
        });
    }
}
