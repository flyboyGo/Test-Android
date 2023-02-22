package com.example.databinding.base.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databinding.R;
import com.example.databinding.base.enity.Planet;
import com.example.databinding.databinding.RecycleViewItemBinding;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Planet> planetList;

    public RecycleViewAdapter(List<Planet> planetList) {
        this.planetList = planetList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecycleViewItemBinding recycleViewItemBinding =  DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                                                          R.layout.recycle_view_item,parent,false);
        return new MyViewHolder(recycleViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Planet planet = planetList.get(position);
        holder.recycleViewItemBinding.setPlanet(planet);
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private RecycleViewItemBinding recycleViewItemBinding;

        public MyViewHolder(RecycleViewItemBinding recycleViewItemBinding) {
            super(recycleViewItemBinding.getRoot());
            this.recycleViewItemBinding = recycleViewItemBinding;
        }
    }

}
