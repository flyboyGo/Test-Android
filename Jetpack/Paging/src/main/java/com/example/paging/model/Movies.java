package com.example.paging.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {

    // 当前返回的数量
    public int count;
    // 起始位置
    public int start;
    // 一共多少条
    public int total;

    @SerializedName("subjects")
    public List<Movie> movieList;

    @Override
    public String toString() {
        return "Movies{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", movieList=" + movieList +
                '}';
    }
}
