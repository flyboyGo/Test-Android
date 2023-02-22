package com.example.paging3.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.paging3.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insertMovies(List<Movie> movieList);

    @Query("DELETE FROM movie")
    void clear();

    @Query("SELECT * FROM movie")
    DataSource.Factory<Integer,Movie> getMovieList();
}
