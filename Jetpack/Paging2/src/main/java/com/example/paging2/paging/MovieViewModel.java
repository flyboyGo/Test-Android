package com.example.paging2.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.paging2.model.Movie;


public class MovieViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> moviePagedListLiveData;

    public MovieViewModel(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false) // 设置控件的占位,默认为true
                .setPageSize(MoviePageKeyedDataSource.PER_PAGE) // 设置每一页的数据的大小
                .setPrefetchDistance(2)// 设置当距离底部还有多少条数据时开始加载下一页(例如一页数据为10,屏幕显示7,剩余3,向下滑动时,当剩余2时,获取下一页数据)
                .setInitialLoadSizeHint(MoviePageKeyedDataSource.PER_PAGE * 2) // 设置首次加载的数量
                .setMaxSize(65536 * MoviePageKeyedDataSource.PER_PAGE)
                .build();
        moviePagedListLiveData = new LivePagedListBuilder<>(new MovieDataSourceFactory(),config).build();
    }
}
