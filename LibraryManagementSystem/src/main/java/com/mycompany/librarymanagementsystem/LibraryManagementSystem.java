package com.mycompany.librarymanagementsystem;

import javax.swing.SwingUtilities;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}
