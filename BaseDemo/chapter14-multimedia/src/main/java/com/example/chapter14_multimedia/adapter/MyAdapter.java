package com.example.chapter14_multimedia.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapter14_multimedia.SoundPoolActivity;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<SoundPoolActivity.Sound> soundList;
    private final Context context;
    private OnItemClickListener listener;
    private RecyclerView recyclerView;

    public MyAdapter(List<SoundPoolActivity.Sound> data, RecyclerView recyclerView, Context context)
    {
        this.soundList = data;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 18;
        params.leftMargin = 18;
        textView.setLayoutParams(params);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(recyclerView.getChildAdapterPosition(v));
            }
        });
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(listener != null)
        {
            ((TextView)holder.itemView).setText(soundList.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return soundList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
