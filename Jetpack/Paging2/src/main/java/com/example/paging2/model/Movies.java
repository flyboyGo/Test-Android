package com.example.paging2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    // 当前返回的数量
    @SerializedName("has_more")
    public boolean hasMore;

    @SerializedName("subjects")
    public List<Movie> movieList;

    @Override
    public String toString() {
        return "Movies{" +
                "hasMore=" + hasMore +
                ", movieList=" + movieList +
                '}';
    }
}
