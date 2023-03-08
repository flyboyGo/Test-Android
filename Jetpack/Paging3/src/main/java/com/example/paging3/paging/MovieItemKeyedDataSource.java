package com.example.paging3.paging;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;

import com.example.paging3.api.RetrofitClient;
import com.example.paging3.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieItemKeyedDataSource extends ItemKeyedDataSource<Integer,Movie> {

    public static final int PER_PAGE = 8;

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Movie> callback) {
        int since = 0;
        RetrofitClient.getInstance()
                .getApi().getMovies(0,PER_PAGE)
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        if(response.body() != null){
                            // 把数据传递给PagedList
                            callback.onResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Movie> callback) {
        RetrofitClient.getInstance()
                .getApi().getMovies(params.key,PER_PAGE)
                .enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                        if(response.body() != null){
                            // 把数据传递给PagedList
                            callback.onResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Movie>> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Movie> callback) {

    }

    // Movie对象中的id作为key
    @NonNull
    @Override
    public Integer getKey(@NonNull Movie item) {
        return item.getId();
    }
}
