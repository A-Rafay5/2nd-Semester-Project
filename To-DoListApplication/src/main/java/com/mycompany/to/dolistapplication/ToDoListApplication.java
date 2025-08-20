package com.mycompany.to.dolistapplication;

import javax.swing.SwingUtilities;

public class ToDoListApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}
