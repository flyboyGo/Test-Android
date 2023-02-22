package com.example.paging.paging;

import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.paging.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie>{

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        return new MoviePositionalDataSource();
    }
}
