package com.example.chapter07_extra.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chapter07_extra.R;
import com.example.chapter07_extra.enity.Planet;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private Context context;
    private  List<Planet> planetList;
    private LayoutInflater layoutInflater;

    public RecycleViewAdapter(Context context, List<Planet> planetList)
    {
        this.context = context;
        this.planetList = planetList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.recycle_view_item_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {
        Planet planet = planetList.get(position);
        holder.iv.setImageResource(planet.image);
        // 屏幕只能识别px,200我们想要显示的是200dp,但实际是200px
        holder.iv.setLayoutParams(new LinearLayout.LayoutParams(200,200));
        holder.tv_desc.setText(planet.desc);
        holder.tv_name.setText(planet.name);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "您点击了 " + planetList.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv;
        TextView tv_desc;
        TextView tv_name;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv = itemView.findViewById(R.id.iv_icon);
            this.tv_name = itemView.findViewById(R.id.tv_name);
            this.tv_desc = itemView.findViewById(R.id.tv_desc);
            this.linearLayout = itemView.findViewById(R.id.item);
        }
    }
}
