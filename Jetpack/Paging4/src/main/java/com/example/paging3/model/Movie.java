package com.example.paging3.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "movie")
public class Movie {

    @PrimaryKey
    @ColumnInfo(name = "no", typeAffinity = ColumnInfo.INTEGER)
    private int NO;
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private int id;
    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    private String title;
    @ColumnInfo(name = "rate", typeAffinity = ColumnInfo.TEXT)
    private String rate;
    @ColumnInfo(name = "cover", typeAffinity = ColumnInfo.TEXT)
    private String cover;

    @Ignore
    public Movie() {
    }

    public Movie(int NO, int id, String title, String rate, String cover) {
        this.NO = NO;
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.cover = cover;
    }

    @Ignore
    public Movie(int id, String title, String rate, String cover) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.cover = cover;
    }

    @Ignore
    public Movie(String title, String rate, String cover) {
        this.title = title;
        this.rate = rate;
        this.cover = cover;
    }

    public int getNO() {
        return NO;
    }

    public void setNO(int NO) {
        this.NO = NO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "NO=" + NO +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", rate='" + rate + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return NO == movie.NO && id == movie.id && title.equals(movie.title) && rate.equals(movie.rate) && cover.equals(movie.cover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NO, id, title, rate, cover);
    }
}
