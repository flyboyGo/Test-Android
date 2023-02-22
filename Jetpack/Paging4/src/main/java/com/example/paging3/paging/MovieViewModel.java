package com.example.paging3.paging;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.paging3.db.MovieDao;
import com.example.paging3.db.MyDatabase;
import com.example.paging3.model.Movie;


public class MovieViewModel extends AndroidViewModel {

    public LiveData<PagedList<Movie>> moviePagedListLiveData;
    public static final int PER_PAGE = 8;


    public MovieViewModel(@NonNull Application application) {
        super(application);

        MovieDao movieDao = MyDatabase.getInstance(application).getMovieDao();

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false) // 设置控件的占位,默认为true
                .setPageSize(PER_PAGE) // 设置每一页的数据的大小
                .setPrefetchDistance(2)// 设置当距离底部还有多少条数据时开始加载下一页(例如一页数据为10,屏幕显示7,剩余3,向下滑动时,当剩余2时,获取下一页数据)
                .setInitialLoadSizeHint(PER_PAGE * 2) // 设置首次加载的数量
                .setMaxSize(65536 * PER_PAGE)
                .build();

        moviePagedListLiveData = new LivePagedListBuilder<>(movieDao.getMovieList(),PER_PAGE)
                .setBoundaryCallback(new MovieBoundaryCallback(application))
                .build();
    }

    /**
     * 刷新数据
     */
    public void refresh(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MyDatabase.getInstance(getApplication())
                        .getMovieDao()
                        .clear();
            }
        });
    }
}
