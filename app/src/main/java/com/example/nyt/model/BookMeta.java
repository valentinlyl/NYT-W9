package com.example.nyt.model;

import java.util.List;

public class BookMeta {
    private List<Book> books;

    public BookMeta(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
