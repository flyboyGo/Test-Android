package com.example.paging.api;

import com.example.paging.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("getMovies")
    Call<Movies> getMovies(@Query("start") int since, @Query("count") int pageSize);
}
