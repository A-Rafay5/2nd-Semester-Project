package com.mycompany.librarymanagementsystem;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private int year;
    private String isbn;
    private String description; 
    private boolean isBorrowed;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public Book(String title, String author, int year, String isbn, String description) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;  // Initialize ISBN
        this.description = description;  // Initialize Description
        this.isBorrowed = false;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    // Borrow and return methods
    public void borrow() {
        this.isBorrowed = true;
        this.issueDate = LocalDate.now();
    }

    public void returnBook(LocalDate returnDate) {
        this.isBorrowed = false;
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + year + ")";
    }
}
