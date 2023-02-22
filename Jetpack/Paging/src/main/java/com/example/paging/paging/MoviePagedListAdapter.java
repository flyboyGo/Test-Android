package com.example.paging.paging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paging3.R;
import com.example.paging.model.Movie;
import com.squareup.picasso.Picasso;

public class MoviePagedListAdapter extends PagedListAdapter<Movie, MoviePagedListAdapter.MovieViewHolder> {

    private Context context;

    // 差分更新，只更新需要更新的元素,而不是刷新整个数据源
    private static final  DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    public  MoviePagedListAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycle_view, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        if(movie != null){
            Picasso.get()
                    .load(movie.getCover())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
            if(movie.getTitle().length() >= 8)
            {
                holder.tv_title.setText(movie.getTitle().substring(0,7));
            }

            holder.tv_rate.setText(movie.getRate());
        }
    }

    static  class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView tv_rate;
        private TextView tv_title;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.tv_title = itemView.findViewById(R.id.tv_title);
            this.tv_rate = itemView.findViewById(R.id.tv_rate);
        }
    }
}
