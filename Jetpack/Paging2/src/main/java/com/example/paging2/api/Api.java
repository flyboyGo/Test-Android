package com.example.paging2.api;


import com.example.paging2.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("getMovies")
    Call<Movies> getMovies(@Query("page") int page, @Query("pagesize") int pageSize);
}
