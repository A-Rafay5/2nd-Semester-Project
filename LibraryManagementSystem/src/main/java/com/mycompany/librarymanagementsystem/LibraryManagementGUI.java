package com.mycompany.librarymanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LibraryManagementGUI extends JFrame {

    private DefaultTableModel tableModel;

    public LibraryManagementGUI() {
        setTitle("Library Management System");
        setSize(1000, 700);
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel with Logout Button
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(255, 69, 58)); // Red background
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> {
            this.dispose(); // Close the current GUI
            new LoginGUI().setVisible(true); // Open the Login GUI
        });
        topPanel.add(logoutButton, BorderLayout.EAST);
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(topPanel, BorderLayout.NORTH);

        // Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel manageBooksPanel = createManageBooksPanel();
        JPanel viewBooksPanel = createViewBooksPanel();

        // Add Panels to TabbedPane
        tabbedPane.addTab("Manage Books", manageBooksPanel);
        tabbedPane.addTab("View Books", viewBooksPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createManageBooksPanel() {
        JPanel manageBooksPanel = new JPanel(new BorderLayout());
        manageBooksPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel headerLabel = new JLabel("Add New Book", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setForeground(new Color(51, 153, 255));
        manageBooksPanel.add(headerLabel, BorderLayout.NORTH);

        // Form Panel with GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Book Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels for Form Fields
        JLabel titleLabel = new JLabel("Book Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField();
        JLabel isbnLabel = new JLabel("ISBN:");
        JTextField isbnField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");

        // Style Labels to be Bold
        Font labelFontBold = new Font("Arial", Font.BOLD, 14);
        titleLabel.setFont(labelFontBold);
        authorLabel.setFont(labelFontBold);
        yearLabel.setFont(labelFontBold);
        isbnLabel.setFont(labelFontBold);
        descriptionLabel.setFont(labelFontBold);

        // JTextArea for Description with ScrollPane
        JTextArea descriptionArea = new JTextArea(3, 20); // Reduced height (3 lines)
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Adding labels and fields to form
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        titleField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(titleField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(authorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(yearLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(yearField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(isbnLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(isbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionScrollPane, gbc); // Add JScrollPane here

        // Set Field Sizes
        titleField.setPreferredSize(new Dimension(200, 30));
        authorField.setPreferredSize(new Dimension(200, 30));
        yearField.setPreferredSize(new Dimension(200, 30));
        isbnField.setPreferredSize(new Dimension(200, 30));
        descriptionScrollPane.setPreferredSize(new Dimension(200, 80)); // Reduced height for description area

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addBookButton = new JButton("Add Book");
        addBookButton.setBackground(new Color(51, 153, 255));
        addBookButton.setForeground(Color.WHITE);
        addBookButton.setFont(new Font("Arial", Font.BOLD, 14));
        addBookButton.setPreferredSize(new Dimension(200, 40));
        addBookButton.setFocusPainted(false);
       addBookButton.addActionListener(e -> {
    try {
        // Validate input fields
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearText = yearField.getText().trim();
        String isbn = isbnField.getText().trim();
        String description = descriptionArea.getText().trim(); // Use JTextArea text

        if (title.isEmpty() || author.isEmpty() || yearText.isEmpty() || isbn.isEmpty() || description.isEmpty()) {
            throw new Exception("All fields must be filled!");
        }
        if (!title.matches("[a-zA-Z0-9- ]+")) {
            throw new Exception("Title can only contain letters, numbers, spaces, and hyphens (no other special characters allowed).");
        }
        if (!author.matches("[a-zA-Z ]+")) {
            throw new Exception("Author can only contain letters.");
        }
        if (!yearText.matches("\\d{4}")) {
            throw new Exception("Year must be a 4-digit number.");
        }
        if (!isbn.matches("\\d{13}")) {
            throw new Exception("ISBN must be exactly 13 digits.");
        }

        // Check if the ISBN already exists in the table
        boolean isbnExists = false;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String existingIsbn = (String) tableModel.getValueAt(i, 4);
            if (existingIsbn.equals(isbn)) {
                isbnExists = true;
                break;
            }
        }

        if (isbnExists) {
            throw new Exception("ISBN already exists in the system.");
        }

        // Add book to table
        tableModel.addRow(new Object[]{false, title, author, yearText, isbn, "No", "N/A", "N/A"});
        JOptionPane.showMessageDialog(this, "Book Added Successfully!");

        // Clear fields
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        isbnField.setText("");
        descriptionArea.setText(""); // Clear JTextArea
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});

        buttonPanel.add(addBookButton);

        manageBooksPanel.add(formPanel, BorderLayout.CENTER);
        manageBooksPanel.add(buttonPanel, BorderLayout.SOUTH);

        return manageBooksPanel;
    }

    private JPanel createViewBooksPanel() {
        JPanel viewBooksPanel = new JPanel(new BorderLayout());
        viewBooksPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Search Bar for ISBN
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Search by ISBN: ");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(cancelButton);
        viewBooksPanel.add(searchPanel, BorderLayout.NORTH);

        // Table for Books
        String[] columnNames = {"Select", "Title", "Author", "Year", "ISBN", "Borrowed", "Issue Date", "Return Date"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class; // Checkbox column
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0; // Only checkbox column is editable
            }
        };

        JTable booksTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(booksTable);

        // Ensure multiple checkboxes can be selected
        booksTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Set column widths to improve visibility
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(50); // Checkbox column
        booksTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Title column

        // Search Button Functionality
        searchButton.addActionListener(e -> {
            String searchIsbn = searchField.getText().trim();
            boolean found = false;

            DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public Class<?> getColumnClass(int column) {
                    return column == 0 ? Boolean.class : String.class; // Checkbox column remains Boolean
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0; // Only checkbox column is editable
                }
            };

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String isbn = (String) tableModel.getValueAt(i, 4); // Get ISBN from the table
                if (isbn.equals(searchIsbn)) {
                    Object[] rowData = new Object[tableModel.getColumnCount()];
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        rowData[j] = tableModel.getValueAt(i, j); // Copy the row data including the checkbox value
                    }
                    filteredModel.addRow(rowData); // Add matching row to the filtered model
                    found = true;
                }
            }

            if (found) {
                booksTable.setModel(filteredModel); // Update the table with filtered model
            } else {
                JOptionPane.showMessageDialog(this, "Result not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Make sure only a single selection is allowed, so no unexpected behaviors occur
        booksTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Search Button Functionality
        searchButton.addActionListener(e -> {
            String searchIsbn = searchField.getText().trim();
            boolean found = false;
            DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public Class<?> getColumnClass(int column) {
                    return column == 0 ? Boolean.class : String.class; // Checkbox column remains Boolean
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 0; // Only checkbox column is editable
                }
            };

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String isbn = (String) tableModel.getValueAt(i, 4); // Get ISBN from the table
                if (isbn.equals(searchIsbn)) {
                    Object[] rowData = new Object[tableModel.getColumnCount()];
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        rowData[j] = tableModel.getValueAt(i, j); // Copy the row data including the checkbox value
                    }
                    filteredModel.addRow(rowData); // Add matching row to the filtered model
                    found = true;
                }
            }

            if (found) {
                booksTable.setModel(filteredModel); // Update the table with filtered model
            } else {
                JOptionPane.showMessageDialog(this, "Result not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel Button Functionality
        cancelButton.addActionListener(e -> {
            booksTable.setModel(tableModel); // Reset to the full list
            searchField.setText(""); // Clear the search field
            // Ensure the selection is cleared when you cancel the search
            booksTable.clearSelection();
        });
        // Buttons at the Bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton borrowButton = new JButton("Mark Book As Borrowed");
        JButton returnButton = new JButton("Mark Book As Returned");
        JButton deleteButton = new JButton("Delete");

        // Delete Button Functionality
        deleteButton.addActionListener(e -> {
            boolean isAnyRowSelected = false;
            DefaultTableModel currentModel = (DefaultTableModel) booksTable.getModel(); // Current (filtered or full) model
            DefaultTableModel originalModel = tableModel; // Original (full) model

            for (int i = currentModel.getRowCount() - 1; i >= 0; i--) {
                if ((boolean) currentModel.getValueAt(i, 0)) { // If the checkbox is selected
                    isAnyRowSelected = true;
                    String isbnToDelete = (String) currentModel.getValueAt(i, 4); // Get the ISBN of the row to delete

                    // Remove the row from both models
                    currentModel.removeRow(i); // Remove from filtered model
                    for (int j = 0; j < originalModel.getRowCount(); j++) {
                        if (originalModel.getValueAt(j, 4).equals(isbnToDelete)) {
                            originalModel.removeRow(j); // Remove from original model based on ISBN
                            break;
                        }
                    }
                }
            }

            if (!isAnyRowSelected) {
                JOptionPane.showMessageDialog(this, "No book selected!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Selected book(s) have been deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Borrow Button Functionality
        borrowButton.addActionListener(e -> {
            boolean isAnyRowSelected = false;
            boolean isAnyBookBorrowed = false; // Flag to track if any book was successfully borrowed
            DefaultTableModel currentModel = (DefaultTableModel) booksTable.getModel(); // Get the current table model (filtered or full)

            for (int i = 0; i < currentModel.getRowCount(); i++) {
                if ((boolean) currentModel.getValueAt(i, 0)) { // Check if the checkbox is selected
                    isAnyRowSelected = true;

                    // Check if already borrowed
                    if (currentModel.getValueAt(i, 5).equals("Yes")) {
                        JOptionPane.showMessageDialog(this, "This book is already borrowed.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }

                    // Mark as borrowed
                    currentModel.setValueAt("Yes", i, 5); // Set Borrowed Status
                    currentModel.setValueAt(getCurrentDate(), i, 6); // Set Issue Date
                    isAnyBookBorrowed = true; // Mark that a book was successfully borrowed

                    // Update the corresponding row in the original tableModel
                    String isbn = (String) currentModel.getValueAt(i, 4); // Get the ISBN of the current row
                    for (int j = 0; j < tableModel.getRowCount(); j++) {
                        if (tableModel.getValueAt(j, 4).equals(isbn)) {
                            tableModel.setValueAt("Yes", j, 5); // Update Borrowed Status
                            tableModel.setValueAt(getCurrentDate(), j, 6); // Update Issue Date
                            break;
                        }
                    }
                }
            }

            // Show success message only if at least one book was borrowed
            if (isAnyBookBorrowed) {
                JOptionPane.showMessageDialog(this, "Book(s) marked as borrowed!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (!isAnyRowSelected) { // Show error message if no book was selected
                JOptionPane.showMessageDialog(this, "No book selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Return Button Functionality
        returnButton.addActionListener(e -> {
            boolean isAnyRowSelected = false;
            DefaultTableModel currentModel = (DefaultTableModel) booksTable.getModel(); // Get the current table model (filtered or full)

            for (int i = 0; i < currentModel.getRowCount(); i++) {
                if ((boolean) currentModel.getValueAt(i, 0)) { // Check if the checkbox is selected
                    isAnyRowSelected = true;

                    // Check if the book is borrowed first
                    if (currentModel.getValueAt(i, 5).equals("No")) {
                        int response = JOptionPane.showConfirmDialog(this, "Borrow the book first?", "Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                        if (response == JOptionPane.OK_OPTION) {
                            // Mark as Borrowed after confirmation
                            currentModel.setValueAt("Yes", i, 5); // Mark as borrowed
                            currentModel.setValueAt(getCurrentDate(), i, 6); // Set Issue Date
                        }
                        continue;
                    }

                    // Mark as returned
                    currentModel.setValueAt(getCurrentDate(), i, 7); // Set Return Date
                    currentModel.setValueAt("No", i, 5); // Mark as not borrowed

                    // Update the corresponding row in the original tableModel
                    String isbn = (String) currentModel.getValueAt(i, 4); // Get the ISBN of the current row
                    for (int j = 0; j < tableModel.getRowCount(); j++) {
                        if (tableModel.getValueAt(j, 4).equals(isbn)) {
                            tableModel.setValueAt(getCurrentDate(), j, 7); // Update Return Date
                            tableModel.setValueAt("No", j, 5); // Update Borrowed Status
                            break;
                        }
                    }
                }
            }

            // Adjusting message based on selection
            if (isAnyRowSelected) {
                JOptionPane.showMessageDialog(this, "Book(s) marked as returned!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No book selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(borrowButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(deleteButton);

        viewBooksPanel.add(scrollPane, BorderLayout.CENTER);
        viewBooksPanel.add(buttonPanel, BorderLayout.SOUTH);

        return viewBooksPanel;
    }

// Helper function to get the current date as String
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementGUI().setVisible(true));
    }
}
