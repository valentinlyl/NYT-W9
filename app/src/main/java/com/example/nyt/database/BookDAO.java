package com.example.nyt.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nyt.model.Book;

import java.util.List;

@Dao
public interface BookDAO {
    @Query("SELECT * FROM Book")
    List<Book> getBooks();

    @Query("SELECT * FROM Book WHERE isbn = :isbn")
    Book getBookByISBN(long isbn);

    @Insert
    void insert(Book book);

    @Delete
    public void delete(Book book);

    @Query("SELECT COUNT(*) from book")
    int getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Book> books);

}
