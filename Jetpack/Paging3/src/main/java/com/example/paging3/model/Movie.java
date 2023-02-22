package com.example.paging3.model;

import java.util.Objects;

public class Movie {

    private int id;
    private String title;
    private String rate;
    private String cover;

    public Movie() {
    }

    public Movie(String title, String rate, String cover) {
        this.title = title;
        this.rate = rate;
        this.cover = cover;
    }

    public Movie(int id, String title, String rate, String cover) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.cover = cover;
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
                "id=" + id +
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
        return id == movie.id && title.equals(movie.title) && rate.equals(movie.rate) && cover.equals(movie.cover);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, rate, cover);
    }
}
