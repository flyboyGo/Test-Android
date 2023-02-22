package com.example.paging2.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.paging2.model.Movie;


public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie>{

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        return new MoviePageKeyedDataSource();
    }
}
