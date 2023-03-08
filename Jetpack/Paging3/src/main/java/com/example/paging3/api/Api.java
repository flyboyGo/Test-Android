package com.example.paging3.api;


import com.example.paging3.model.Movie;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("getMovies")
    Call<List<Movie>> getMovies(@Query("since") int page, @Query("pagesize") int pageSize);
}
