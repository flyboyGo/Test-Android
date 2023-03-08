package com.example.paging3.paging;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.example.paging3.api.RetrofitClient;
import com.example.paging3.db.MyDatabase;
import com.example.paging3.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<Movie> {

    private Application application;
    public static final int PER_PAGE = 8;

    public MovieBoundaryCallback(Application application) {
        this.application = application;
    }

    // 加载首页数据
    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        
        getTopData();
    }
    private void getTopData() {
        int since = 0;
        RetrofitClient.getInstance()
                .getApi().getMovies(0,PER_PAGE)
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        if(response.body() != null){
                            // 从网络获取数据,保存到数据库中
                            insertMovies(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });
    }


    // 加载下一页数据
    @Override
    public void onItemAtEndLoaded(@NonNull Movie itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        getNextData(itemAtEnd);
    }
    private void getNextData(Movie movie) {
        RetrofitClient.getInstance()
                .getApi().getMovies(movie.getId(),PER_PAGE)
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        if(response.body() != null){
                            // 从网络获取数据,保存到数据库中
                            insertMovies(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });
    }


    // 将获取的网络数据缓存到数据库
    private void insertMovies(List<Movie> movieList) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MyDatabase.getInstance(application).getMovieDao().insertMovies(movieList);
            }
        });
    }
}
