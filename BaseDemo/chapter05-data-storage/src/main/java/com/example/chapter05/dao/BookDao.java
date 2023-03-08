package com.example.chapter05.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.chapter05.enity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insert(Book... book);

    @Delete
    void delete(Book... book);

    @Query("DELETE FROM Book")
    void deleteAll();

    @Update
    int update(Book... book);

    @Query("SELECT * FROM Book")
    List<Book> queryAll();

    @Query("SELECT * FROM Book WHERE name = :name ORDER BY id DESC limit 1")
    List<Book> queryByName(String name);
}
