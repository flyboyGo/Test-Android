package com.example.paging2.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;


import com.example.paging2.api.RetrofitClient;
import com.example.paging2.model.Movie;
import com.example.paging2.model.Movies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePageKeyedDataSource extends PageKeyedDataSource<Integer, Movie> {

    public static final int PER_PAGE = 8;
    public static final int FIRST_PAGE = 1;

    // 加载第一页
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getApi().getMovies(FIRST_PAGE,PER_PAGE)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if(response.body() != null){
                            // 判断是否存在数据
                            // 把数据传递给PagedList
                            if(response.body().hasMore)
                            {
                                callback.onResult(response.body().movieList,null,FIRST_PAGE + 1);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getApi().getMovies(params.key,PER_PAGE)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if(response.body() != null){
                            // 把数据传递给PagedList
                            // 判断是否还有更多的数据
                            Integer nextKey = response.body().hasMore ? params.key + 1 : null;
                            callback.onResult(response.body().movieList,nextKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {

                    }
                });
    }
}
