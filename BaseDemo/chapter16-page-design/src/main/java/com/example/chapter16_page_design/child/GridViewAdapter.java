package com.example.chapter16_page_design.child;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.chapter16_page_design.R;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private int[] images;
    private String[] text;
    private int[] color;

    public GridViewAdapter(Context context, int[] images, String[] text, int[] color) {
        this.context = context;
        this.images = images;
        this.text = text;
        this.color = color;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridViewAdapter.MyViewHolder holder;
        if(convertView == null){
            holder = new GridViewAdapter.MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sound,null);
            holder.imageView = convertView.findViewById(R.id.image_view_sound_id);
            holder.textView = convertView.findViewById(R.id.tv_sound_name);
            holder.linearLayout = convertView.findViewById(R.id.layout_sound_item_container);
            convertView.setTag(holder);
        }else{
            holder = (GridViewAdapter.MyViewHolder)convertView.getTag();
        }

        holder.imageView.setImageResource(images[position]);
        holder.textView.setText(text[position]);
        GradientDrawable gradientDrawable = (GradientDrawable) context.getDrawable(R.drawable.shape_item_sound);
        gradientDrawable.setCornerRadius(15);
        gradientDrawable.setColor(context.getResources().getColor(color[position], context.getTheme()));
        holder.linearLayout.setBackground(gradientDrawable);
        holder.linearLayout.invalidate();
        return convertView;
    }

    protected  class MyViewHolder{
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;
    }
}
