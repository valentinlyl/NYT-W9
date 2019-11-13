package com.example.nyt.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    // ISBN can be our ID for books (we use isbn13 instead of isbn10)
    @SerializedName("primary_isbn13")
    @PrimaryKey
    @NonNull
    private long isbn;

    private int rank;
    private String description;
    private String title;
    private String author;

    @SerializedName("book_image")
    private String bookImage;

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public void setBuyLinks(List<BuyLink> buyLinks) {
        this.buyLinks = buyLinks;
    }

    @SerializedName("buy_links")
    @Ignore
    private List<BuyLink> buyLinks;

    public long getIsbn() {
        return isbn;
    }

    public int getRank() {
        return rank;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookImage() {
        return bookImage;
    }

    @Ignore
    public List<BuyLink> getBuyLinks() {
        return buyLinks;
    }

    // Here I decide to write BuyLink as an inner class rather than in a separate file, because
    // it's very unlikely that BuyLink will be important anywhere else. It will always be part of a
    // book, and only part of a book.
    public class BuyLink {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }
    }
}