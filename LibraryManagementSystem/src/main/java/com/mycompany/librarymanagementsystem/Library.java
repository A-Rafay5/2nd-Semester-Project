package com.mycompany.librarymanagementsystem;

import java.time.LocalDate;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(String title, String author, int year, String isbn, String description) {
        books.add(new Book(title, author, year, isbn, description));
    }

    public ArrayList<Book> getAllBooks() {
        return books;
    }

    public Book searchBookByTitle(String title) throws CustomException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        throw new CustomException("Book not found!");
    }

    public void markAsBorrowed(String title) throws CustomException {
        Book book = searchBookByTitle(title);
        if (book.isBorrowed()) {
            throw new CustomException("Book is already borrowed!");
        }
        book.borrow();
    }

    public void markAsReturned(String title, LocalDate returnDate) throws CustomException {
        Book book = searchBookByTitle(title);
        if (!book.isBorrowed()) {
            throw new CustomException("Book is not borrowed!");
        }
        book.returnBook(returnDate);
    }
}
